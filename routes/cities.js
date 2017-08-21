var express = require('express');
var router = express.Router();

var City = require('../models/city');

router.get('/cities', function(req, res) {
    City.find(req.query, {  _id: 0, __v: 0 }, function (err, cities) {
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
    City.find({ capital: true }, {  _id: 0, __v: 0 }, { sort: { name: 1 } }, (err, cities) => {
        if(err) 
            res.status(500).json(err);
        else {
            res.json(cities);
        }
    });
});

router.get('/cities/distinct', function(req, res) {
    City.find().distinct(req.query.col, (err, result) => {
        if(err) 
            res.status(500).json(err);
        else {
            res.json({
                column: req.query.col, 
                count: result.length 
            });
        }
    });
});

router.get('/cities/state', function(req, res) {
    City.find({ uf: req.query.uf }, {  _id: 0, name: 1 }, function (err, cities) {
        if(err) {
            res.status(500).json(err);
            return;
        }
        else if(!(req.query.uf)) {
            res.status(400).json({
                msg: "Nenhum estado selecionado. Use o parâmetro ?uf=[estado].",
                query: req.query
            });
        }
        else if(!cities.length) {
            res.status(404).json({
                msg: "Nenhuma cidade encontrada para o estado selecionado.",
                state: req.query.uf
            });
        }
        else {
            let cities_list = [];
            cities.forEach(city => cities_list.push(city.name));
            res.json(cities_list);
        }
    });
});

router.get('/cities/state/count', function(req, res) {
    City.aggregate([
        { $group: { _id: '$uf', total: { $sum: 1 } } }, 
	    { $sort: { _id: 1 } }
    ],
    (err, result) => {
        if(err) {
            res.status(500).json(err);
            return;
        }
        res.json(result);
    });
});

router.get('/cities/state/count/min-max', function(req, res) {
    City.aggregate([
        { $group: { _id: '$uf', total: { $sum: 1 } } }, 
	    { $sort: { total: 1 } }
    ],
    (err, result) => {
        if(err) {
            res.status(500).json(err);
            return;
        }

        let first = 0;
        let last = result.length - 1;
        res.json({
            min: {
                uf: result[first]._id,
                total: result[first].total
            },
            max: {
                uf: result[last]._id,
                total: result[last].total
            }
        });
    });
});

router.post('/cities', (req, res) => {
    let city = new City();
    city.ibge_id           = req.body.ibge_id;
    city.uf                = req.body.uf;
    city.capital           = req.body.capital;
    city.name              = req.body.name;
    city.no_accents        = req.body.no_accents;
    city.alternative_names = req.body.alternative_names;
    city.microregion       = req.body.microregion;
    city.mesoregion        = req.body.mesoregion;
    city.location          = { type: "Point", coordinates: [req.body.lon, req.body.lat] };

    city.save((err) => {
        if(err) {
            if(err.code === 11000 || err.code === 11001)
                res.status(409).json({ msg: `Conflito de dados: Já existe uma cidade cadastrada com ibge_id informado (${req.body.ibge_id}).` });
            
            else
                res.status(500).json(err);
            
            return;
        }

        res.status(201).json(city);
    });
    
});

router.delete('/cities/:ibge_id', (req, res) => {
    City.findOneAndRemove({ ibge_id: req.params.ibge_id }, (err, city) => {
        if(err) {
            res.status(500).json(err);
            return;
        }

        if(!city) {
            res.status(404).json({ msg: `Nenhuma cidade encontrada. (ibge_id: ${req.params.ibge_id})` });
            return;
        }

        res.status(204).json({});
    });
});

module.exports = router;