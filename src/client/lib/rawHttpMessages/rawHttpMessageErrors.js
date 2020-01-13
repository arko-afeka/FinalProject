exports.MessageIsNotReadyError = class MessageIsNotReadyError extends Error {
    constructor() {
        super('Raw http message is not ready, more data is missing.');
    }
};

exports.AbstractHttpMessageInit = class AbstractHttpMessageInit extends Error {
    constructor() {
        super('This class is abstract. Use HttpRequest or HttpResponse classes.');
    }
};

exports.AbstractHttpMessageMethod = class AbstractHttpMessageMethod extends Error {
    constructor() {
        super('This method is abstract, and need to be overrided on child classes.');
    }
};