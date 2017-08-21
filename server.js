var express    = require('express');
var app        = express();
var bodyParser = require('body-parser');

// -------------------- MONGODB CONFIG --------------------

require('./config/mongo');

// ------------------ MIDLLEWARE CONFIG ------------------

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// -------------------- ROUTER CONFIG --------------------

var cities = require('./routes/cities');
var distances = require('./routes/distances');

app.use((err, req, res, next) => {
    if(err instanceof SyntaxError) {
        res.status(400).json({ msg: 'JSON inválido' });
        return;
    }

    next();
});

app.use('/api', cities);
app.use('/api', distances);


module.exports = app;