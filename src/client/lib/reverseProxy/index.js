/**
 * Module dependencies.
 * @private
 */

const http = require("http"),
    middlewareHandler = require("../middlewareHandler"),
    reverseProxyErrors = require('./reverseProxyErrors'),
    log = require('../log');

class ReverseProxy {
    /**
     * Initialize a new `ReverseProxy`.
     *
     * @param {object} [options] Proxy's options
     * @param {string} [options.targetHost] The target host to proxy
     * @param {number} [options.port] The target port
     * @param {boolean} [options.log] enable logger
     * @api public
     */

    constructor(options) {
        this.global = options;
        this.targetHost = options.targetHost;

        this.requestHandler = middlewareHandler();

        // Use this.global object to the request object middleware.
        this.use('request', (req, res, next) => {
            req.global = this.global;
            next();
        });

        if (this.global.log) {
            this.use('request', log.logIncommingRequest);
        }

        this.server = http.createServer();

        this.server.on("request", this.requestHandler);

        this.server.on("request", (req, res) => {
            res.writeHead(200);
            res.write("hello world!");
            res.end();
        });
    }

    /**
     * Append a middleware to an event emitted on this.server.
     * For example:
     * ReverseProxy.prototype.use('request', (req, res) => { console.log(`A new ${req.method} request`); });
     * will print to the console that a new 'request' event is emitted.
     *
     * @param {string} event A http.Server event
     * @param {function} fn Middleware function to bind to listener
     * @public
     */

    use(event, fn) {
        var handler = {
            request: this.requestHandler
        } [event];

        // A single middleware function.
        if (typeof fn === "function") {
            handler.use(fn);
            return;
        }
        // An array of middleware functions
        if (Array.isArray(fn)) {
            fn.forEach(fn => {
                handler.use(fn);
                return;
            });
        }
        // Otherwise throw an error
        throw new reverseProxyErrors.middlewareIsNotAFunctionError();
    }

    /**
     * Shorthand for this.server.listen(...)
     *
     * @param {Mixed} ...
     * @return {Server}
     * @api public
     */

    listen(...args) {
        return this.server.listen(...args);
    }
}

/**
 * Module exports.
 * @public
 */

module.exports = ReverseProxy;