exports.middlewareIsNotAFunctionError = class MiddlewareIsNotAFunctionError extends Error {
    constructor() {
        super('ReverseProxy.prototype.use paramater must be a function or an array of functions');
    }
};