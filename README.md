# geolocation-api

API que retorna as coordenadas a partir de uma string de endereço, ou a partir de uma latitude e longitude.



Essa API foi criada para ser usada em aplicações que necessitem de uma funcionalidade de geolocalização. Ela é baseada na API do Google Maps, que é uma das principais provedoras de dados de localização no mundo.

A API  usa um  rate limited para não ultrapassar as requisições de 1000 por dia e 30 mil por mês. Isso é para evitar que a API seja usada de forma indevida e para evitar que a conta do Google Maps seja bloqueada.
para criar as credenciais use o seguinte link: https://console.developers.google.com/apis/credentials

## criando a chave
* https://developers.google.com/maps/documentation/embed/get-api-key?hl=pt-br
* https://console.cloud.google.com/project/_/google/maps-apis/credentials?utm_source=Docs_CreateAPIKey&utm_content=Docs_maps-embed-backend&hl=pt-br

## Instalando o projeto em Docker

Para instalar o projeto em Docker, faça as seguintes etapas:

1. Abra o terminal e execute o comando `mvn spring-boot:build-image -e -DGOOGLE_MAPS_API_KEY=<seu_valor>` para buildar a imagem do Docker.
2. Execute o comando `docker images` para verificar se a imagem foi buildada com sucesso.
3. Execute o comando `docker run -e GOOGLE_MAPS_API_KEY=<sua_chave> -p 8081:8081 geolocation-api:0.0.1-SNAPSHOT` para rodar o container.
4. Acesse o endereço http://localhost:8081/ para acessar o projeto.

OBS: O nome da imagem   gerado automaticamente pelo comando `mvn spring-boot:build-image`, por isso   mostrado como `<nome-da-imagem>`.
## Rodando o projeto com Docker

Para rodar o projeto com Docker, faça as seguintes etapas:

1. Execute o comando `docker build -t geolocation-api .` para buildar a imagem do Docker.
2. Execute o comando `docker run -p 8081:8081 geolocation-api` para rodar o container.
3. Acesse o endere o http://localhost:8081/ para acessar o projeto.

OBS: O projeto usa o GraalVM, que é uma JVM que roda nativamente e precisa de uma imagem do Docker que tenha o GraalVM como runtime. Para isso, foi adicionado o arquivo `Dockerfile` no projeto, que é  responsável por buildar a imagem do Docker com o GraalVM.

## Variáveis de Ambiente

Para que o projeto funcione,   necessário que a variável de ambiente `GOOGLE_MAPS_API_KEY` esteja setada com a chave do Google Maps.

Para setar a variável de ambiente, execute o comando: `export GOOGLE_MAPS_API_KEY=<chave>`


## Dockerfile para rodar a aplicação com Java 22
```
FROM openjdk:22-jdk-alpine as build
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go -DskipTests

COPY src ./src
RUN ./mvnw package -DskipTests

FROM openjdk:22-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
```


## Consumindo a API
```
curl --location 'http://localhost:8081/geocoding/geocode' \
--header 'Content-Type: application/json' \
--data '{
  "address": "906 Rua jardins bairro novo, Porto Velho, RO"
}'
```

## Requisição Reverso
```
curl --location 'http://localhost:8081/geocoding/reverse' \
--header 'Content-Type: application/json' \
--data '{
  "latitude": -8.785307,
  "longitude": -63.849245
}'
```

# Documentação da API

 - para acessar a documentação da API use o seguinte link: 
   - http://localhost:8081/swagger-ui.html
