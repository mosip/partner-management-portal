const { createProxyMiddleware } = require('http-proxy-middleware');
module.exports = function (app) {
    app.use(
        '/api',
        createProxyMiddleware({
            target: process.env.NODE_ENV !== "production" ? process.env.REACT_APP_API_BASE_URL : process.env.REACT_APP_API_BASE_URL,
            changeOrigin: false,
            secure: process.env.NODE_ENV !== "production" ? false : true,
            pathRewrite: {
                "^/api": ""
            }
        })
    );
};