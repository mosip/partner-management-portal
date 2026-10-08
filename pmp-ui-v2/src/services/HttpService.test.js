jest.mock('axios', () => jest.requireActual('axios/dist/node/axios.cjs'));
jest.mock('./LoginRedirectService.js', () => ({ getLoginRedirectUrl: jest.fn() }));
jest.mock('./UserProfileService.js', () => ({ setUserProfile: jest.fn(), getUserProfile: jest.fn() }));
jest.mock('./ConfigService.js', () => ({ getAppConfig: jest.fn(() => ({ axiosTimeout: 1 })) }));
jest.mock('jwt-decode', () => ({ jwtDecode: jest.fn() }));

import { AxiosHeaders } from 'axios';
import { HttpService } from './HttpService';

const respondWith = (token, seen) => (config) => {
  seen.push(config.headers.get('X-XSRF-TOKEN'));
  return Promise.resolve({
    data: {},
    status: 200,
    statusText: 'OK',
    headers: token ? { 'x-xsrf-token': token } : {},
    config,
  });
};

describe('HttpService masked CSRF token', () => {
  const originalAdapter = HttpService.defaults.adapter;

  afterEach(() => {
    HttpService.defaults.adapter = originalAdapter;
  });

  it('sends no masked token until the API has sent one', async () => {
    const seen = [];
    HttpService.defaults.adapter = respondWith(null, seen);

    await HttpService.post('/v1/partnermanager/test', {});

    expect(seen).toEqual([undefined]);
  });

  it('sends the latest masked token from the previous response on the next request', async () => {
    const seen = [];
    HttpService.defaults.adapter = respondWith('masked-1', seen);
    await HttpService.get('/v1/partnermanager/test');

    HttpService.defaults.adapter = respondWith('masked-2', seen);
    await HttpService.post('/v1/partnermanager/test', {});

    HttpService.defaults.adapter = respondWith(null, seen);
    await HttpService.put('/v1/partnermanager/test', {});

    expect(seen).toEqual([undefined, 'masked-1', 'masked-2']);
  });

  it('stops axios from sending the raw cookie value once a masked token is held', async () => {
    HttpService.defaults.adapter = respondWith('masked-3', []);
    await HttpService.get('/v1/partnermanager/test');

    expect(HttpService.defaults.withXSRFToken()).toBe(false);
  });

  it('keeps the masked token from an error response', async () => {
    const seen = [];
    HttpService.defaults.adapter = (config) =>
      Promise.reject({
        response: { status: 403, headers: new AxiosHeaders({ 'x-xsrf-token': 'masked-4' }), config },
        config,
      });
    await expect(HttpService.post('/v1/partnermanager/test', {})).rejects.toBeTruthy();

    HttpService.defaults.adapter = respondWith(null, seen);
    await HttpService.post('/v1/partnermanager/test', {});

    expect(seen).toEqual(['masked-4']);
  });
});
