var mongoose   = require('mongoose');

var mongo_host = process.env.MONGO_HOST || 'localhost';
var mongo_port = process.env.MONGO_PORT || 27017;
var mongo_db = process.env.MONGO_DB || 'city-finder';

var mongo_uri = `mongodb://${mongo_host}:${mongo_port}/${mongo_db}`;

mongoose.Promise = global.Promise;
mongoose.connect(mongo_uri, {
    useMongoClient: true,
    autoReconnect: true,
    reconnectTries: Number.MAX_VALUE,
    reconnectInterval: 1000,
    keepAlive: 1,
    connectTimeoutMS: 30000
});

mongoose.connection.on('connecting', function() {
    console.log('Connecting to database @', mongo_uri);
})
mongoose.connection.on('connected', function() {
    console.log('Mongoose connected');
});
mongoose.connection.on('error', function(err) {
    console.log('Mongoose connection error: ' + err);
});
mongoose.connection.on('timeout', function(e) {
    console.log("db: mongodb timeout " + e);
});

var closeConnection = function(msg, callback) {
    mongoose.connection.close(function() {
        console.log('Mongoose disconnected through ' + msg);
        callback();
    });
}

process.once('SIGUSR2', function() {
    closeConnection('nodemon restart', function() {
        process.kill(process.pid, 'SIGUSR2');
    });
});
process.on('SIGINT', function() {
    closeConnection('app termination', function() {
        process.exit(0);
    });
});
process.on('SIGTERM', function() {
    closeConnection('Heroku app shutdown', function() {
        process.exit(0);
    })
});