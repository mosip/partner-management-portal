import { render } from '@testing-library/react';
import App from './App';

jest.mock('./AppRoutes', () => () => <div data-testid="app-routes" />);
jest.mock('./services/ConfigService.js', () => ({ loadAppConfig: jest.fn(() => Promise.resolve()) }));

test('renders without crashing', () => {
  render(<App />);
});
