/**
 * Module dependencies.
 * @private
 */

var grpc = require('grpc');
var protoLoader = require('@grpc/proto-loader');
var config = require('../../config');

/**
 * Module varibles.
 * @private
 */

var PROTO_PATH = __dirname + '/api.proto';
var {
    host,
    port
} = config.waf;
var packageDefinition = protoLoader.loadSync(PROTO_PATH);
var packageObject = grpc.loadPackageDefinition(packageDefinition);

/**
 * Module exports.
 * @public
 * @returns {object}
 */

module.exports = new packageObject.api.ServerAPI(`${host}:${port}`, grpc.credentials.createInsecure());