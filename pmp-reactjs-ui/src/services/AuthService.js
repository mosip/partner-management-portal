import { HttpService } from "../services/HttpService";
import { getPartnerManagerUrl } from "../utils/AppUtils";

export const isAuthenticated = async () => {
    try {
        await HttpService
            .get(getPartnerManagerUrl(`/authorize/admin/validateToken`, process.env.NODE_ENV));
        console.log(`isAuthenticated: yes`);
        return { token: true };
    } catch (err) {
        console.log('isAuthenticated error:', err);
        return { token: false };
    }
}