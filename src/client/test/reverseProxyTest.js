var assert = require("assert");
var http = require("http");
var proxy = require("../lib/reverseProxy");

var mocServerStatus = 215;
var mockServerMsg = 'mock server';
var mockServerHost = 'localhost';
var mockServerPort = '3000';
var mockServerProtocol = 'http:';
var mockServerTestHeader = 'testheader';
var mockServerTestHeaderValue = 'testvalue';

var proxyHost = 'localhost';
var proxyPort = 3001;
var useValue = 'test';

var mockServer = http.createServer((req, res) => {
    res.setHeader(mockServerTestHeader, mockServerTestHeaderValue)
    res.writeHead(mocServerStatus);
    res.write(mockServerMsg);
    res.end();
});

var proxy = new proxy({
    targetHost: {
        host: mockServerHost,
        port: mockServerPort,
        protocol: mockServerProtocol
    },
    host: proxyHost,
    port: proxyPort,
    blocking: true
});

describe("reverseProxy", () => {
    before((done) => {
        mockServer.listen(3000, () => {
            proxy.listen(() => {
                done();
            });
        });
    });

    describe("forwarding", function () {
        it("forwards status code", function () {
            var request = http.request('http://localhost:3001/', (res) => {
                assert(res.statusCode === mocServerStatus, 'response status code');
            });
            request.end();
        });

        it("forwards header", function () {
            var request = http.request('http://localhost:3001/', (res) => {
                assert(res.headers[mockServerTestHeader] === mockServerTestHeaderValue, 'response header');
            });
            request.end();
        });

        it("forwards response data", function () {
            var request = http.request('http://localhost:3001/', (res) => {
                let data = '';
                res.on('data', (chunk) => {
                    data += chunk.toString();
                });
                res.on('end', () => {
                    assert(data === mockServerMsg, 'response data');
                });
            });
            request.end();
        });
    });

    describe('middlewares', () => {
        it("uses middleware", () => {
            proxy.use((req, res, next) => {
                req.test = useValue;
                next();
            });
            proxy.on('request', (req, res) => {
                assert(req.test === useValue, 'proxy use middleware')
            });
            var request = http.request('http://localhost:3001/');
            request.end();
        });

        it("throws an error when middleware is not a function", () => {
            assert.throws(() => {
                proxy.use("not a function");
            }, require('../lib/reverseProxy/reverseProxyErrors').MiddlewareIsNotAFunctionError, 'middleware is not a function');
        });
    });

    after((done) => {
        proxy.close(() => {
            mockServer.close(() => {
                done();
            });
        });
    });
});