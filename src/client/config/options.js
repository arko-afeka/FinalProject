const reverseProxyOptions = require('../lib/reverseProxy/reverseProxyOptions');

module.exports = reverseProxyOptions.create({
    targetHost: 'www.google.com',
    port: 80,
    log: true
});