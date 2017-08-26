# Getting started

## Requisitos

1. NodeJS v6.x **ou** Java v1.7+
2. MongoDB v3.x 

## Clonando repositório GIT

```bash
$ git clone git@github.com:rsorage/city-finder.git
```

O projeto será baixado na pasta `city-finder`.

### Branches

Atualmente existem duas branches para o projeto.

* `master`: Projeto original em NodeJS
* `java`: Nova versão utilizando Java com Spring Framework

#### Utilizando a versão em NodeJS

Entre na pasta do projeto e execute o comando `npm install` para que as dependências do NodeJS sejam instaladas e configuradas.

```bash
$ cd city-finder
$ npm install
```

#### Utilizando a versão em Java

Mudando de branch:

```bash
$ git checkout java
```

Utilize então o tutorial fornecido na branch `java`.

## Importando dados para o MongoDB

Nessa etapa nosso banco de dados será populado. 

**Atenção:** No MongoDB será criado um banco de dados chamado `city-finder` e uma collection chamada `cities`. Caso já existentes, **os dados anteriores serão sobrescritos**. Para mudar essas configurações padrão, altere a linha 27 do arquivo `config.sh`. O parâmetro da opção -d especifica o nome do banco e a opção -c a collection.

```bash
echo "### Importando dados do CSV..."
mongoimport -d city-finder -c cities --type csv cities.csv --headerline --ignoreBlanks --drop 
```

Certifique-se que o MongoDB está instalado em seu sistema e o serviço rodando. Então execute o arquivo `scripts/config.sh`.

```bash
$ sudo service mongod start
$ cd scripts
$ ./mongo_config.sh
```

## Rodando o servidor localmente

Na raíz do projeto execute o comando `npm run start` e o servidor iniciará na porta 3000.

```bash
$ npm run start

> city-finder@1.0.0 start /home/rsorage/workspaces/nodejs/city-finder
> node ./bin/www

Server listening on port 3000
```

# Desafio

1. Ler o arquivo CSV das cidades para a base de dados
    * Foi explicado na seção **Importando dados para o MongoDB**

2. Retornar somente as cidades que são capitais ordenadas por nome

    ```
    GET /api/cities/capitals HTTP/1.1
    ```

3. Retornar o nome do estado com a maior e menor quantidade de cidades e a quantidade de cidades

    ```
    GET /api/cities/state/count/min-max HTTP/1.1
    ```

4. Retornar a quantidade de cidades por estado

    ```
    GET /api/cities/state/count HTTP/1.1
    ```

5. Obter os dados da cidade informando o id do IBGE

    ```
    GET /api/cities?ibge_id=2800308 HTTP/1.1
    ```

6. Retornar o nome das cidades baseado em um estado selecionado

    ```
    GET /api/cities?uf=PB HTTP/1.1
    ```

7. Permitir adicionar uma nova cidade

    ```
    # Request
    POST /api/cities HTTP/1.1

    # Response
    {
        "ibge_id": 4200200,
        "uf": "SC",
        "name": "Agrolândia",
        "no_accents": "Agrolandia",
        "microregion": "Ituporanga",
        "mesoregion": "Vale do Itajaí",
        "lon": -49.8256533877,
        "lat": -27.400516588
    }
    ```

    **Respostas possíveis:**

        | Status code   | Significado                             |
        | ------------- | --------------------------------------- |
        | 201           | Cidade criada                           |
        | 400           | Problema com JSON enviado na requisição |
        | 409           | Conflito: código do IBGE já existe      |
        | 500           | Erro do servidor                        |

8. Permitir deletar uma cidade

    ```
    DELETE /api/cities?ibge_id=2800308 HTTP/1.1
    ```

    **Respostas possíveis:**

        | Status code   | Significado                            |
        | ------------- | -------------------------------------- |
        | 204           | Cidade deletada                        |
        | 404           | Nenhuma cidade encontrada              |
        | 500           | Erro do servidor                       |

9. Permitir selecionar uma coluna (do CSV) e através dela entrar com uma string para filtrar retornar assim todos os objetos que contenham tal string

    ```
    GET /api/cities?name=Aguiar&uf=PB HTTP/1.1
    ```

    **Respostas possíveis:**

        | Status code   | Significado                            |
        | ------------- | -------------------------------------- |
        | 200           | Ok                                     |
        | 404           | Nenhuma cidade encontrada              |
        | 500           | Erro do servidor                       |

10. Retornar a quantidade de registro baseado em uma coluna. Não deve contar itens iguais.

    ```
    # Request
    GET /api/cities/distinct?col=uf HTTP/1.1

    # Response
    {
        "column": "uf",
        "count": 27
    }
    ```

11. Retornar a quantidade de registros total

    ```
    # Request
    GET /api/cities/count HTTP/1.1

    # Response
    {
        "query": {},
        "found": 5564
    }
    ```

12. Dentre todas as cidades, obter as duas cidades mais distantes uma da outra com base na localização (distância em KM em linha reta)

    ```
    # Request
    GET /api/cities/distance/max HTTP/1.1

    # Response
    {
        "cities": [
            {
                "ibge_id": 1200336,
                "uf": "AC",
                "name": "Mncio Lima",
                "no_accents": "Mancio Lima",
                "microregion": "Cruzeiro do Sul",
                "mesoregion": "Vale do Juru",
                "location": {
                    "type": "Point",
                    "coordinates": [
                        -72.9165010261,
                        -7.5932225939
                    ]
                }
            },
            {
                "ibge_id": 2605459,
                "uf": "PE",
                "name": "Fernando de Noronha",
                "no_accents": "Fernando de Noronha",
                "microregion": "Fernando de Noronha",
                "mesoregion": "Metropolitana de Recife",
                "location": {
                    "type": "Point",
                    "coordinates": [
                        -32.4351863281,
                        -3.8520214008
                    ]
                }
            }
        ],
        "distance": 4496.374518259978
    }
    ```