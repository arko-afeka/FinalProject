/**
 * Module exports.
 * @public
 */

exports.AnalysisStatusResponseError = class AnalysisStatusResponseError extends Error {

    constructor() {
        super('Problem with getting the analysis status.')
    }
}