/**
 * Module dependencies.
 * @private
 */

var http = require("http");
var ProxyIncoming = require("../proxyIncoming");
var ProxyResponse = require("../proxyResponse");
var middlewaresHandler = require("../middlewaresHandler");
var reverseProxyErrors = require('./reverseProxyErrors');
var requestAnalyzer = require('../requestAnalyzer');

class ReverseProxy extends http.Server {

    /**
     * @param {object} options 
     * @param {object} options.targrtHost target host to proxy properties
     * @param {String} options.targrtHost.host  target host ip address
     * @param {Number} options.targrtHost.port target host port number
     * @param {String} options.targrtHost.protocol 'http:' | 'https:'
     * @param {String} host proxy host ip address
     * @param {String} port proxy port number
     * @param {Boolean} blocking true | false to run on blocking | not-blocking modez
     * @constructor
     * @public
     */
    constructor(options) {
        const handler = middlewaresHandler();

        super({
                IncomingMessage: ProxyIncoming,
                ServerResponse: ProxyResponse
            },
            handler
        );

        this.global = options;
        this.handler = handler;
        this.requestAnalyzer = requestAnalyzer;

        this.use((req, res, next) => {
            req.global = this.global;
            res.global = this.global;
            next();
        });

        if (this.global.blocking) {
            this.use((req, res, next) => {
                req.requestAnalyzer = this.requestAnalyzer;
                next();
            });
        }
    }

    /**
     * Add a middleware function to the handler's middlewares stack,
     * to be used when the handler is called.
     * An array of functions can also be passed, they will be added
     * to the stack one by one.
     * 
     * @param {Function} fn 
     * @public
     */

    use(fn) {
        if (typeof fn === "function") {
            this.handler.use(fn);
            return;
        }

        // An array of middleware functions.
        if (Array.isArray(fn)) {
            fn.forEach(fn => {
                this.handler.use(fn);
                return;
            });
        }

        throw new reverseProxyErrors.MiddlewareIsNotAFunctionError();
    }

    /**
     * Request analysis.
     * @param {String} data raw http request
     * @param {Function} cb callback function that take analysisStatus proto as param
     */

    analyzeRequest(data, cb) {
        this.requestAnalyzer(data, cb);
    }

    /**
     * Init server listening for connections.
     * 
     * @override
     * @public
     */

    listen(cb) {
        cb = cb ? cb : () => {
            console.log(`proxy server started on ${this.global.host}:${this.global.port}`);
        }
        super.listen(this.global.port, this.global.host, cb);
    }
}

/**
 * Module exports.
 * @public
 */

module.exports = ReverseProxy;