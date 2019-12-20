const reverseProxy = require('./lib/reverseProxy'),
    options = require('./config/options');

var app = new reverseProxy(options);

app.listen(8081, () => {
    console.log(`Server started!`)
});