/**
 * Analysis respone status codes.
 * @constant
 * @private
 */

const CONSTANTS = {
  sp: " ",
  httpVersionPrefix: "HTTP/",
  crlf: "\r\n",
  headerSp: ": "
};

class ProxyIncomigRaw {

  /**
   * 
   * @param {ProxyIncomig} request client request received
   * @public
   */

  constructor(request) {
    this.startLine = "";
    this.headersField = "";
    this.body = "";
    this.hasBody = false;

    let {
      method,
      url,
      httpVersion,
      headers
    } = request;

    this.setupStartLine(method, url, httpVersion);
    this.setupHeadersField(headers);
  }

  /**
   * 
   * @param {String} method
   * @param {String} path
   * @param {String} httpVersion
   * @public
   */

  setupStartLine(method, path, httpVersion) {
    this.startLine =
      method +
      CONSTANTS.sp +
      path +
      CONSTANTS.sp +
      CONSTANTS.httpVersionPrefix +
      httpVersion +
      CONSTANTS.crlf;
  }

  /**
   * 
   * @param {object} headers header: headerValue
   * @public 
   */

  setupHeadersField(headers) {
    this.headersField = "";
    Object.keys(headers).forEach(header => {
      this.headersField +=
        header +
        CONSTANTS.headerSp +
        headers[header] +
        CONSTANTS.crlf;
    });
    this.headersField += CONSTANTS.crlf;
  }

  /**
   * Appends data to the body.
   * @param {String} data 
   */

  appendToBody(data) {
    this.hasBody = true;
    this.body += data;
  }

  /**
   * @returns {String}
   * @public
   */

  getRaw() {
    return this.hasBody ? this.startLine + this.headersField + this.body :
      this.startLine + this.headersField;
  }
}

/**
 * Module exports.
 * @public
 */

module.exports = ProxyIncomigRaw;