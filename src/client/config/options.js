const reverseProxyOptions = require('../lib/reverseProxy/reverseProxyOptions');

module.exports = reverseProxyOptions.create({
    host: 'localhost',
    port: 8080,
    log: true
});