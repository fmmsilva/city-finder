/**
 * Convertendo pontos de coordenada (lat, lon) para o tipo Point do MongoDB.
 * Agora os dados serão salvos no seguinte formato:
 *  --> location: { type: "Point", coordinates: [lat, lon] }
 *
 * Isso possibilita a criação de índices geográficos e cálculo de distância mais precisos.
 */

db.cities.find().forEach(function(city) {
    city.location = {
        "type": "Point", 
        "coordinates": [city.lon, city.lat]
    };
		
		// Removendo campos antigos
    delete city.lon;
    delete city.lat;

    // Dado vindo do CSV como string, convertendo aqui para boolean
    if(city.capital == "true")
      city.capital = true; 

		// Salvando campos novos
    db.cities.save(city);
});
