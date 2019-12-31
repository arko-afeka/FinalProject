const rawHttpRequest = require('../rawHttpMessages/httpRequest');

module.exports = (req, res, next) => {
    req.raw = new rawHttpRequest(req);
    next();
}