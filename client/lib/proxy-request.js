const http = require('http');

/*  options <object> {
 *  headers: <object> request headers object header<property>: value<string>
 *  protocol: <string> 'http' / 'https'
 *  host: <string> host address or hostname'
 *  port: <number> host's port to be used
 *  url: <string> relative path of the url
 * }
 */

exports.get = (options, response) => {
    let {
        protocol,
        host,
        port,
        url,
        headers
    } = options;

    // Make a request from the proxy to the target host
    const request = http.request(`${protocol}://${host}:${port}${url}`, {
        headers: headers
    });

    // On response callback
    request.on('response', (targetResponse) => {

        const data = [];

        targetResponse.on('data', (chunk) => {
            data.push(chunk)
        });

        targetResponse.on('end', () => {

            // Set response from proxy
            Object.keys(targetResponse.headers).forEach((header) => {
                response.setHeader(header, targetResponse.headers[header]);
            });
            response.writeHead(targetResponse.statusCode);
            response.write(Buffer.concat(data).toString());
            response.end();
        });
    });
    request.end();
};