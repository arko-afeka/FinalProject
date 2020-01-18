/**
 * Module dependencies.
 * @private
 */

var merge = require('utils-merge');
var proxyRequestRules = require('./proxyRequestRules');

/**
 * Module exports.
 * returns options object for ProxyRequest constructor
 * @public
 * @returns {object}
 */

module.exports.setupOptions = function setupOptions(request) {
    var options = {
        host: request.global.targetHost.host,
        port: request.global.targetHost.port,
        method: request.method,
        path: request.url,
        httpVersion: request.httpVersion,
        request: request
    }

    var headers = {};
    merge(headers, request.headers);

    Object.keys(proxyRequestRules).forEach((rule) => {
        proxyRequestRules[rule](headers, request);
    });
    options.headers = headers;

    return options;
}