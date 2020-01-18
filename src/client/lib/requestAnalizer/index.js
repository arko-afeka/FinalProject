var grpcClient = require("../grpcClient");

module.exports = function (data, cb) {
    let wafResponse = data => {
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
    };

    wafResponse(data)
        .then(analysis => {
            cb ? cb(analysis) : -1;
        })
        .catch(err => {
            cb(err);
        });
};