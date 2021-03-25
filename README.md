# Catálogo de produtos

Implementação de um catálogo de produtos com Java e Spring Boot.

## product-ms

Neste microsserviço é possível criar, alterar, visualizar e excluir um determinado produto, além de visualizar a lista de produtos atuais disponíveis. Também é possível realizar a busca de produtos filtrando por *name, description e price*.

## Pré-requisito
- MAVEN 4
- JAVA 11
- MySQL 5.7.8 (Docker)

## Preparando ambiente
Existe um arquivo docker-compose.yml na raiz do projeto.

Para iniciar o container dkrmysql, rode o comando abaixo na raiz do projeto (no terminal)
```
docker-compose up -d
```
Ou altere os dados de conexão com o banco em:
```
src/main/resources/application.properties (aplicação)
```
E altere os dados de conexão com o banco em:
```
src/main/resources/flyway.properties (migration)
```
Para fazer o build e rodar a aplicação, execute o comando abaixo na raiz do projeto (no terminal)
```
./mvnw clean package
java -jar target/catalogodeprodutos-ms-0.0.1-SNAPSHOT.jar
```
Para rodar os testes de integração, execute o comando abaixo na raiz do projeto (no terminal)
```
./mvnw test
```

## Documentação Swagger

```
http://localhost:8080/swagger-ui.html#/
```

## Agradecimentos

Obrigado pela oportunidade e pelo excelente desafio técnico.