const reverseProxyOptions = require('../lib/reverseProxy/reverseProxyOptions');

module.exports = reverseProxyOptions.create({
    targetHost: {
        host: 'localhost',
        port: 8080
    },
    log: true
});