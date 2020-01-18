/**
 * Module dependencies.
 * @private
 */

var http = require('http');
var merge = require('utils-merge');
var proxyResponseRules = require('./proxyResponseRules');

/**
 * Module constats.
 * @constant
 * @private
 */

const STATUS_CODE_BAD_REQUEST = 400;

class ProxyResponse extends http.ServerResponse {

    /**
     * 
     * @param {ProxyIncoming} req 
     */

    constructor(req) {
        super(req);

        req.on('serverResponse', this.handleServerResponse.bind(this));
        req.on('block', this.block.bind(this));
    }

    /**
     * Response handler, piping the data from the server response,
     * and rewrites rules.
     * @param {IncomingMessage} response 
     * @private
     */

    handleServerResponse(response) {
        response.pipe(this);

        let headers = {};
        merge(headers, response.headers);

        Object.keys(proxyResponseRules).forEach((rule) => {
            proxyResponseRules[rule](headers, this);
        });

        this.writeHead(response.statusCode, headers);
        response.on('end', () => {
            this.end();
        });
    }

    /**
     * 'block' event listener, sends error response to client.
     */

    block() {
        this.writeHead(STATUS_CODE_BAD_REQUEST);
        this.end();
    }
}

/**
 * Module exports.
 * @public
 */

module.exports = ProxyResponse;