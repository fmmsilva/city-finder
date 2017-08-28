# Getting started

## Requisitos

1. Java v1.8+
2. Maven v3.x
3. MongoDB v3.x

## Clonando repositório GIT

```bash
$ git clone git@github.com:rsorage/city-finder.git
```

O projeto será baixado na pasta `city-finder`.

### Branches

Atualmente existem duas branches para o projeto.

* `master`: Projeto original em NodeJS
* `java`: Nova versão utilizando Java com Spring Framework


#### Utilizando a versão em Java

Mudando de branch:

```bash
$ git checkout java
```

## Importando dados para o MongoDB

Seguir os passos descritos na branch `master`.

## Rodando o servidor localmente

Na raíz do projeto execute o comando `mvn spring-boot:run` e o servidor iniciará na porta 8080.

```bash
$ mvn spring-boot:run
```

# Desafio

1. Ler o arquivo CSV das cidades para a base de dados
    * Foi explicado na seção **Importando dados para o MongoDB**

2. Retornar somente as cidades que são capitais ordenadas por nome

    ```
    GET /api/cities/search/findByCapitalIsTrue?sort=name HTTP/1.1
    ```

3. Retornar o nome do estado com a maior e menor quantidade de cidades e a quantidade de cidades

    ```
    GET /api/cities/statesMaxMinCities HTTP/1.1
    ```

4. Retornar a quantidade de cidades por estado

    Retornanado a quantidade de cidades num estado específico.
    ```
    GET /api/cities/search/countByUf{?uf} HTTP/1.1
    ```
    
    Retornando a quantidade de cidades por cada estado.
    ```
    GET /api/cities/countCitiesPerState HTTP/1.1
    ```

5. Obter os dados da cidade informando o id do IBGE

    ```
    GET /api/cities/search/findByIbgeId{?ibge-id} HTTP/1.1
    ```

6. Retornar o nome das cidades baseado em um estado selecionado

    ```
    GET /api/citiessearch/findByUf{?uf,page} HTTP/1.1
    ```

7. Permitir adicionar uma nova cidade

    ```
    # Request
    POST /api/cities HTTP/1.1

    {
        "ibge_id": 4200200,
        "uf": "SC",
        "name": "Agrolândia",
        "no_accents": "Agrolandia",
        "microregion": "Ituporanga",
        "mesoregion": "Vale do Itajaí",
        "location": {
        	"type": "Point",
        	"coordinates": [-49.8256533877, -27.400516588]
        }
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
    GET /api/cities/searchByColumn?col=name&query=Blumenau HTTP/1.1
    ```

10. Retornar a quantidade de registro baseado em uma coluna. Não deve contar itens iguais.

    ```
    # Request
    GET /api/cities/countByColumn?col=uf HTTP/1.1

    # Response
    {
        "column": "uf",
        "total": 27
    }
    ```

11. Retornar a quantidade de registros total

    ```
    # Request
    GET /api/cities/countAllCities HTTP/1.1

    # Response
    5565
    ```

12. Dentre todas as cidades, obter as duas cidades mais distantes uma da outra com base na localização (distância em KM em linha reta)

    ```
    # Request
    GET /api/cities/mostDistant HTTP/1.1

    # Response
    [
        {
            "id": "599a4a644d87b45d1c563f72",
            "name": "Marechal Thaumaturgo",
            "location": {
                "x": -72.7902659087,
                "y": -8.9535911232,
                "coordinates": [
                    -72.7902659087,
                    -8.9535911232
                ],
                "type": "Point"
            },
            "uf": "AC",
            "microregion": "Cruzeiro do Sul",
            "mesoregion": "Vale do Juru",
            "ibge_id": 1200351,
            "no_accents": "Marechal Thaumaturgo"
        },
        {
            "id": "599a4a644d87b45d1c564528",
            "name": "Fernando de Noronha",
            "location": {
                "x": -32.4351863281,
                "y": -3.8520214008,
                "coordinates": [
                    -32.4351863281,
                    -3.8520214008
                ],
                "type": "Point"
            },
            "uf": "PE",
            "microregion": "Fernando de Noronha",
            "mesoregion": "Metropolitana de Recife",
            "ibge_id": 2605459,
            "no_accents": "Fernando de Noronha"
        }
    ]
    ```