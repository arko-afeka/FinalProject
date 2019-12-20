const http = require('http');
const proxyRequest = require('./proxy-request');

const proxyServer = http.createServer();

proxyServer.targetHost = 'localhost';
proxyServer.targetPort = 8080;

// Incomming request
proxyServer.on('request', (request, response) => {
    const {
        method,
        url,
        headers
    } = request, {
        remoteAddress
    } = request.socket;

    console.log(`Incomming ${method} request from ${remoteAddress}`)
    console.log(`For url: ${JSON.stringify(url)}`);


    // Handle forwarding to waf
    /******************implemetation******************/

    // Set the proxy's request options
    let options = {
        headers: headers,
        host: proxyServer.targetHost,
        port: proxyServer.targetPort,
        url: url,
        protocol: 'http'
    }

    if (method == 'GET') {
        proxyRequest.get(options, response);
    }
});

module.exports = proxyServer;