/**
 * Module dependencies.
 * @private
 */

const merge = require("utils-merge");

/**
 * Module variables.
 * @private
 */

var proto = {};

/**
 * Create a new middlewareHandler.
 *
 * @return {function}
 * @public
 */

function createHandler() {
  function app(req, res, next) {
    app.handle(req, res, next);
  }
  merge(app, proto);
  app.stack = [];

  return app;
}

/**
 * Add a middleware function to the middlewares stack.
 * Middleware stracture:
 * function(req, res, next) {
 * ...
 * next();
 * }
 *
 * @param {Function} middleware function
 * @returns {Function} handle function
 * @public
 */

proto.use = function use(fn) {
  var handle = fn;
  this.stack.push(handle);

  return this;
};

/**
 * Handle a http.Server event (request / data / end ...), calling them
 * one after the other.
 *
 * @private
 */

proto.handle = function handle(req, res) {
  var index = 0;
  var stack = this.stack;

  function next() {
    var mw = stack[index++];

    if (!mw) {
      return;
    }

    call(mw, req, res, next);
  }

  next();
};

/**
 * Invoke the middleware function handle.
 * @private
 */

function call(handle, req, res, next) {
  try {
    handle(req, res, next);
  } catch (error) {
    errorHandler(error);
  }

  return;
}

/**
 * Error handler.
 * @param {Error} error 
 */

function errorHandler(error) {
  console.error(error);
}

/**
 * Module exports.
 * @public
 */

module.exports = createHandler;