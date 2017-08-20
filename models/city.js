var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var CitySchema = new Schema({
    ibge_id: { type: String, required: true, unique: true },
    uf: { type: String, required: true },
    name: { type: String, required: true },
    capital: Boolean,
    location: { 
        type: { type: String }, 
        coordinates: [Number]
        //index: { type: '2dsphere', sparse: true } 
    },
    no_accents: String,
    alternative_names: String,
    microregion: String,
    mesoregion: String
});

module.exports = mongoose.model('City', CitySchema);