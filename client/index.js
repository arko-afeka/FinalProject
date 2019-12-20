const app = require('./lib/reverse-proxy');

const port = 5000;

app.listen(port, () => console.log(`server started at port ${port}`));