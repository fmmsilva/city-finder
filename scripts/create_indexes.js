/**
 * Script para criação de índices. 
 */
db.cities.createIndex({ ibge_id: 1 });
db.cities.createIndex({ name: 1 });
db.cities.createIndex({ location: '2dsphere' });
