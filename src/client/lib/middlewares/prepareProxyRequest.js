const http = require('http'),
    proxyRequest = require('../proxyRequest');

module.exports = (req, res, next) => {

    var rq = new proxyRequest(req, res);
    req.pipe(rq);
    req.on('end', () => {
        rq.end();
    });

    next();
};