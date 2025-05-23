import { v4 as uuidv4 } from 'uuid';
import { Cookies } from 'react-cookie';
import { getPartnerManagerUrl } from "../utils/AppUtils";

export const getLoginRedirectUrl = (url) => {
    console.log(url);
    const cookies = new Cookies();
    const stateParam = uuidv4();
    cookies.set('state', stateParam, { path: '/' });
    let url1 = getPartnerManagerUrl(`/login/` + btoa(url) + '?state=' + stateParam, process.env.NODE_ENV);
    return url1;
}