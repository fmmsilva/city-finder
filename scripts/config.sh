#!/bin/bash

# ========================================================================= #
#                                                                           #
#   Script de importação dos dados do CSV para MongoDB.                     #
#                                                                           #
#     * Verifica se o serviço 'mongod' está rodando.                        #
#       - Se não estiver o script é finalizado com status 1.                #
#       - Se estiver, inicia-se a importação dos dados do CSV.              #
#       - Por último o script 'convert_coords.js' é executado.              #
#                                                                           #
#     * Informações sobre MongoDB:                                          #
#       - host:       localhost                                             #
#       - port:       27017                                                 #
#       - database:   city-finder                                           #
#       - collection: cities                                                #
#                                                                           #
# ========================================================================= #

mongo --eval "db.stats()"

RESULT=$?

if [ $RESULT -ne 0 ]; then
	echo "ERRO: O serviço 'mongod' não está rodando..."
	exit 1
else
	echo "### Importando dados do CSV..."
	mongoimport -d city-finder -c cities --type csv cities.csv --headerline --ignoreBlanks --drop

	echo "### Formatando dados no banco..."
	mongo localhost:27017/city-finder format_fields.js

	echo "### Criando índices..."
	mongo localhost:27017/city-finder create_indexes.js

	echo "Pronto!"
fi


