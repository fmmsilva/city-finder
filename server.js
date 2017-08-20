var express    = require('express');
var app        = express();
var bodyParser = require('body-parser');

// -------------------- MONGODB CONFIG --------------------

require('./config/mongo');

// ------------------ MIDLLEWARE CONFIG ------------------

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// -------------------- ROUTER CONFIG --------------------

var index = require('./routes/index');
var cities = require('./routes/cities');

app.use('/api', index);
app.use('/api', cities);

// ---------------- APPLICATION BOOTSTRAP ----------------

module.exports = app;