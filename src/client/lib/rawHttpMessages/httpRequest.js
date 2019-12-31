const AbstractHttpMessage = require('./AbstractHttpMessage');

class HttpRequest extends AbstractHttpMessage {

    constructor(req) {
        super(req);
    }

    setStartLine(req) {
        let {
            method,
            url,
            httpVersion
        } = req

        this.startLine = method + this.SP + url + this.SP + this.HTTP_VERSION_PREFIX + httpVersion + this.CRLF;
    }
}

module.exports = HttpRequest;