/**
 * Module dependencies.
 * @private
 */

var http = require('http');
class ProxyRequest extends http.ClientRequest {

    /**
     * 
     * @param {object} options
     * @public
     */

    constructor(options) {
        super(options);

        this.clientRequest = options.request;

        this.on('response', (response) => {
            this.clientRequest.emit('serverResponse', response);
        });
    }
}

/**
 * Module exports.
 * @public
 */

module.exports = ProxyRequest;