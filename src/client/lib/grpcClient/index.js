const grpc = require('grpc'),
    protoLoader = require('@grpc/proto-loader'),
    config = require('../../config/config');

const PROTO_PATH = __dirname + '/api.proto';
const {
    host,
    port
} = config.wafServer;

const packageDefinition = protoLoader.loadSync(PROTO_PATH);
const packageObject = grpc.loadPackageDefinition(packageDefinition);

module.exports = new packageObject.api.ServerAPI(`${host}:${port}`, grpc.credentials.createInsecure());