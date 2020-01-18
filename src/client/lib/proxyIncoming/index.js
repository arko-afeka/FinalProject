/**
 * Module dependencies.
 * @private
 */

var http = require("http");
var ProxyIncomingRaw = require("./proxyIncomingRaw");
var grpcClient = require("../grpcClient");
var ProxyRequest = require("../proxyRequest");
var proxyRequestOptions = require("../proxyRequest/proxyRequestOptions");

/**
 * Analysis respone status codes.
 * @constant
 * @private
 */

const ANALYSIS_STATUS = {
  block: 0,
  allow: 1
};

class ProxyIncoming extends http.IncomingMessage {

  /**
   * 
   * @param {net.Socket} socket
   * @constructor
   * @public 
   */

  constructor(socket) {
    super(socket);

    this.body = "";
    this.hasBody = false;

    this.on("readable", this.onReadable);

    this.on("end", this.onEnd);

    this.once("analized", this.onAnalized);
  }

  /**
   * 'end' event listener, creates the request's raw data and sends it to analysis.
   * @private
   */

  onEnd() {
    // Running on Blocking mode
    if (this.global.blocking) {
      var rawRequest = new ProxyIncomingRaw(this);
      rawRequest.appendToBody(this.body);

      this.requestAnalyzer(rawRequest.getRaw(), (analysis) => {
        this.onAnalized(analysis);
      });
    } else {
      this.onAnalized({
        status: ANALYSIS_STATUS.allow
      });
    }


    //this.analysis(rawRequest.getRaw());
  }

  /**
   * 'analized' event listener, handling the analysis status result.
   * @param {object} analysis 
   * @private
   */

  onAnalized(analysis) {
    if (analysis.status === ANALYSIS_STATUS.allow) {
      this.creatsProxyRequest();
    } else {
      this.emit("block");
    }
    this.emit("close");
  }

  /**
   * 'readable' event listener, saving recieved data from the client.
   * @private
   */

  onReadable() {
    let data;

    while ((data = this.read())) {
      this.body += data.toString();
    }
    this.hasBody = true;
  }

  /**
   * Creates a promise for handling grpc connection to the WAF's server
   * @param {String} data 
   */

  isValidRequestPromise(data) {
    return new Promise((resolve, reject) => {
      var wafClient = grpcClient;

      wafClient.isValidRequest({
          data: data
        },
        (err, response) => {
          err ? reject(err) : resolve(response);
        }
      );
    });
  }

  /**
   * Request analisys
   * @param {String} data raw request to analize.
   * @private
   */

  analysis(data) {
    let wafResponse = this.isValidRequestPromise;

    wafResponse(data)
      .then(analysis => {
        this.emit("analized", analysis);
      })
      .catch(err => {
        throw new ProxyIncomingError.AnalysisStatusResponseError;
      });
  }

  /**
   * Creates a proxy request for the target server.
   * @private
   */

  creatsProxyRequest() {
    var options = proxyRequestOptions.setupOptions(this);
    var proxyRequest = new ProxyRequest(options);

    this.hasBody ? proxyRequest.write(this.body) : -1;
    proxyRequest.end();
  }
}

/**
 * Module exports.
 * @public
 */

module.exports = ProxyIncoming;