import { HttpService } from "../services/HttpService";
import { getPartnerManagerUrl } from "../utils/AppUtils";

export const loadAppConfig = async () => {
    try {
        const resp = await HttpService.get(getPartnerManagerUrl("/system-config", process.env.NODE_ENV));
        const appConfig = resp.data.response;
        localStorage.setItem('appConfig', JSON.stringify(appConfig));
        console.log("window._env_.REACT_APP_PARTNER_MANAGER_API_BASE_URL " + window._env_.REACT_APP_PARTNER_MANAGER_API_BASE_URL);
    } catch (err) {
        console.log("unable to load configs: " + err);
    }
}

export const getAppConfig = async () => {
    let cachedAppConfig = localStorage.getItem('appConfig');
    if (!cachedAppConfig) {
        await loadAppConfig();
        cachedAppConfig = localStorage.getItem('appConfig');
    }
    return JSON.parse(cachedAppConfig);

}