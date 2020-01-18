var reverseProxy = require('./lib/reverseProxy');
var reverseProxyOptions = require('./config').reverseProxyOptions;

var proxy = new reverseProxy(reverseProxyOptions);

proxy.listen();