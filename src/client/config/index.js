const createReverseProxyOptions = require("../lib/reverseProxy/reverseProxyOptions");

const options = createReverseProxyOptions({
  targetHost: {
    host: "localhost",
    port: 8080,
    protocol: "http:"
  },
  host: "localhost",
  port: 8081,
  blocking: false
});

module.exports = {
  reverseProxyOptions: options,
  waf: {
    host: "localhost",
    port: 8082
  }
};