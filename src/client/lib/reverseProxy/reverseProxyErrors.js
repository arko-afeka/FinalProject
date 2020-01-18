exports.MiddlewareIsNotAFunctionError = class MiddlewareIsNotAFunctionError extends Error {
    constructor() {
        super('Middleware must be a function');
    }
}

exports.TargetHostIsRequired = class TargetHostIsRequired extends Error {
    constructor() {
        super('Target host must be past to reverseProxyOptions');
    }
}