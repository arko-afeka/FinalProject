exports.create = function (options) {
    if (!options.targetHost.host || !options.targetHost.port) {
        throw Error('targetHost host and port are required');
    }

    if (options.hasOwnProperty('log')) {
        options.log = true;
    }

    return options;
}