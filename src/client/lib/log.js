exports.info = (msg) => {
    console.log(`INFO: ${msg}`);
};

exports.error = (msg) => {
    console.error(`ERROR: ${msg}`);
};

exports.logIncommingRequest = (req, res, next) => {
    this.info(`Incomming ${req.method} request for ${JSON.stringify(req.url)}`);
    next();
};