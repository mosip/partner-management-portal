import { v4 as uuidv4 } from 'uuid';
import { Cookies } from 'react-cookie';
import { getUrl } from "../utils/AppUtils";

export const loginRedirect = (url) => {
    console.log(url);
    const cookies = new Cookies();
    const stateParam = uuidv4();
    cookies.set('state', stateParam, { path: '/' });
    let url1 = getUrl(`/login/` + btoa(url) + '?state=' + stateParam, process.env.NODE_ENV);
    console.log(url1);
    window.location.href = url1;
}