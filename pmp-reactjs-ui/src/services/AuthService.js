import HttpService from "./HttpService.js";

export const isAuthenticated = async () => {
    try {
        const resp = await HttpService
            .get(`/api/authorize/admin/validateToken`);
            console.log(`isAuthenticated: yes`);
        return { token: true };
    } catch (err) {
        console.log('isAuthenticated error:', err);
        return { token: false };
    }
}