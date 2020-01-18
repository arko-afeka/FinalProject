/**
 * Module dependencies.
 * @private
 */

var reverseProxyErrors = require('./reverseProxyErrors');
var merge = require('utils-merge');

/**
 * Module exports.
 * @public
 * @returns {object}
 */

module.exports = function createReverseProxyOptions(options) {
    var reverseProxyOptions = {};
    merge(reverseProxyOptions, options);

    if (!options || !options.targetHost || !options.targetHost.host) {
        throw new reverseProxyErrors.TargetHostIsRequired();
    }

    reverseProxyOptions.targetHost.host = options.targetHost.host;
    reverseProxyOptions.targetHost.port = options.targetHost.port || 80;
    reverseProxyOptions.targetHost.protocol = options.targetHost.protocol || 'http:';
    reverseProxyOptions.blocking = options.blocking || false;

    return reverseProxyOptions;
};