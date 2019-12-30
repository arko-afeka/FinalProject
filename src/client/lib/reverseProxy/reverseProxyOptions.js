exports.create = function (options) {
    if (!options.host) {
        throw Error('targetHost is required');
    }
    return options;
}