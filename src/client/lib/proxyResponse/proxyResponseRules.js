/**
 * Module constants.
 * @constant
 * @private
 */

const URL_PROTOCOL_SP = '//';
const PATH_SP = '/';
const HOST_PORT_SP = ':';

/**
 * Module exports.
 * Client Request rewriting rules for the proxy's request
 * @public
 * @returns {object}
 */

module.exports = {
    locationHeader: (headers, response) => {
        if (headers.location) {
            let splited = headers.location.split(URL_PROTOCOL_SP);
            let pathIndex = splited[1].indexOf(PATH_SP);
            headers.location = splited[0] + URL_PROTOCOL_SP + response.global.host + HOST_PORT_SP + response.global.port + splited[1].substring(pathIndex);
        }
    }
}