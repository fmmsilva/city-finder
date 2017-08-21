var express = require('express');
var router = express.Router();

var City = require('../models/city');

var GeoPoint = require('geopoint');


router.get('/cities/distance/max', function(req, res) {

    // Para minimizar a quantidade de dados enviados do banco para a aplicação, primeiro são solicitadas somente 
    // informações necessárias (ibge_id e coordenadas) para fazer os cálculos de distância entre as cidades.   
    City.find({ }, { _id: 0, ibge_id: 1, 'location.coordinates': 1 }, (err, cities) => {
        if(err) 
            res.status(500).json(err);
        else {
            let result = calculateMaxDistance(cities);

            City.find({ 
                $or: [
                    { ibge_id: result.cityA.ibge_id }, 
                    { ibge_id: result.cityB.ibge_id }
                ]
            }, 
            { _id: 0, __v: 0 }, 
            (err, cities) => {
                if(err)
                    res.status(500).json(err);
                
                res.json({ cities: cities, distance: result.distance });
            });
        }
    });
});


function calculateMaxDistance(cities) {
    let result = { cityA: null, cityB: null };
    let maxDistance = 0;

    for(let i = 0; i < cities.length; i++) {
        let cityA = cities[i];
        let coordsCityA = new GeoPoint(cityA.location.coordinates[1], cityA.location.coordinates[0]);

        // Iniciando j em i + 1 para evitar fazer o mesmo cálculo de distância duas vezes: dist(A, B) == dist(B, A)
        for(let j = i+1; j < cities.length; j++) {
            let cityB = cities[j];
            let coordsCityB = new GeoPoint(cityB.location.coordinates[1], cityB.location.coordinates[0]);

            let distance = coordsCityA.distanceTo(coordsCityB, true);
            if(distance > maxDistance) {
                maxDistance = distance;
                result.cityA = cityA;
                result.cityB = cityB;
                result.distance = distance;
            }
        }
    }

    return result;
}

module.exports = router