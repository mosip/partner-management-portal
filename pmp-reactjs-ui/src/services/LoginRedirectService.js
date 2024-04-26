import { v4 as uuidv4 } from 'uuid';
import { Cookies } from 'react-cookie';

export const loginRedirect = (url) => {
    console.log(url);
    const cookies = new Cookies();
    const stateParam = uuidv4();
    cookies.set('state', stateParam, {
        path: '/'
    });
    let url1 = `/api/login/` +
        btoa(url) +
        '?state=' +
        stateParam;
    console.log(url1);
    window.location.href = url1;
}