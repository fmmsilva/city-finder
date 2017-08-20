var express = require('express');
var router = express.Router();

var City = require('../models/city');

// router.get('/cities', function(req, res) {
//     City.find(function (err, cities) {
//         if(err) 
//             res.status(500).json(err);
//         else 
//             res.json(cities);
//     });
// });

router.get('/cities', function(req, res) {
    City.find(req.query, {  _id: 0 }, function (err, cities) {
        if(err) {
            res.status(500).json(err);
            return;
        }
        else if(!cities.length) {
            res.status(404).json({
                msg: "Nenhuma cidade encontrada com os parâmetros buscados.",
                query: req.query
            });
        }
        else 
            res.json(cities);
    });
});

router.get('/cities/count', function(req, res) {
    City.find(req.query, function (err, cities) {
        if(err) {
            res.status(500).json(err);
            return;
        }
        else if(!cities.length) {
            res.status(404).json({
                msg: "Nenhuma cidade encontrada com os parâmetros buscados.",
                query: req.query
            });
        }
        else {
            res.json({ 
                query: req.query, 
                found: cities.length 
            });
        }
    });
});

router.get('/cities/capitals', function(req, res) {
    City.find({ capital: true }, {  _id: 0 }, { sort: { name: 1 } }, (err, cities) => {
        if(err) 
            res.status(500).json(err);
        else {
            res.json(cities);
        }
    });
});


module.exports = router;