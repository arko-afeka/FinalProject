const http = require('http');

class ProxyRequest extends http.ClientRequest {

    constructor(req, res) {

        /**
         * Initialize a new `ProxyRequest`.
         *
         * @param {http.IncommingMessage} [req]
         * @param {http.ServerResponse} [res]
         * @api public
         */

        super({
            host: req.global.targetHost.host,
            port: req.global.targetHost.port,
            path: req.url,
            method: req.method,
            headers: req.headers
        });

        this.on('response', (response) => {
            res.writeHead(response.statusCode, response.headers);
            response.pipe(res);
            response.on('end', () => {
                res.end();
            });
        });
    }
}

module.exports = ProxyRequest;