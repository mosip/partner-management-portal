import { HttpService } from "../services/HttpService";
import { getUrl } from "../utils/AppUtils";

export const isAuthenticated = async () => {
    try {
        await HttpService
            .get(getUrl(`/authorize/admin/validateToken`, process.env.NODE_ENV));
        console.log(`isAuthenticated: yes`);
        return { token: true };
    } catch (err) {
        console.log('isAuthenticated error:', err);
        return { token: false };
    }
}