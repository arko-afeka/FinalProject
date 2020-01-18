/**
 * Module exports.
 * Client Request rewriting rules for the proxy's request
 * @public
 * @returns {object}
 */

module.exports = {
    hostHeader: function (headers, request) {
        headers['host'] = `${request.global.targetHost.host}:${request.global.targetHost.port}`;
    },
    forwardedHeader: function (headers, request) {
        headers['Forwarded'] = `for=${request.socket.remoteAddress}:${request.socket.remotePort}`;
    }
};