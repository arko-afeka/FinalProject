const rawHttpMessageErrors = require('./rawHttpMessageErrors');

class AbstractHttpMessage {

    constructor(httpMsg) {
        if (this.constructor === AbstractHttpMessage) {
            throw new rawHttpMessageErrors.AbstractHttpMessageInit();
        }
        this.startLine = '';
        this.setStartLine(httpMsg);

        this.headers = '';
        this.setHeaders(httpMsg.rawHeaders);

        this.body = '';
        httpMsg.on('data', (chunk) => {
            this.body += chunk.toString();
        });

        this.ready = false;

        httpMsg.on('end', () => {
            this.ready = true;
        });

    }

    setStartLine(httpMsg) {
        throw new rawHttpMessageErrors.AbstractHttpMessageMethod();
    }

    setHeaders(headersArray) {
        this.headers = '';

        for (let i = 0; i < headersArray.length; i++) {
            this.headers += i % 2 == 0 ? headersArray[i] + this.HEADER_SP : headersArray[i] + this.CRLF;
        }
    }

    getRaw() {
        if (this.ready) {
            return {
                data: this.startLine + this.headers + this.body
            }
        }

        throw new rawHttpMessageErrors.MessageIsNotReady();
    }
}

AbstractHttpMessage.prototype.SP = ' ';
AbstractHttpMessage.prototype.CRLF = '\r\n';
AbstractHttpMessage.prototype.HTTP_VERSION_PREFIX = 'HTTP/';
AbstractHttpMessage.prototype.HEADER_SP = ': ';

module.exports = AbstractHttpMessage;