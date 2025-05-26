const { createProxyMiddleware } = require('http-proxy-middleware');
module.exports = function (app) {
    app.use(
        '/partnerapi',
        createProxyMiddleware({
            target: process.env.REACT_APP_PARTNER_MANAGER_API_BASE_URL,
            changeOrigin: false,
            secure: false,
            pathRewrite: {
                "^/partnerapi": ""
            }
        })
    );
    app.use(
        '/policyapi',
        createProxyMiddleware({
            target: process.env.REACT_APP_POLICY_MANAGER_API_BASE_URL,
            changeOrigin: false,
            secure: false,
            pathRewrite: {
                "^/policyapi": ""
            }
        })
    );
};