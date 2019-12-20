const http = require('http');

class ProxyRequest extends http.ClientRequest {

    constructor(options) {

        /**
         * Initialize a new `ProxyRequest`.
         *
         * @param {object} [options] ProxyRequest's options
         * @param {string} [options.host] requested host
         * @param {number} [options.port] requested port
         * @param {string} [options.path] requested url path
         * @param {string} [options.remoteHost] request client address
         * @param {string} [options.method] request http method
         * @param {string} [options.protocol] request protocol ( 'http:' / 'https:' )
         * @param {object} [options.headers] request headers object ('header': 'value' )
         * @api public
         */

        return http.request(options);
    }
}

module.exports = ProxyRequest;