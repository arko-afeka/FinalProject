exports.create = function (options) {
    if (!options.targetHost) {
        throw Error('targetHost is required');
    }
    return options;
}