# AlgaFood API

## Description
API containing the main characteristics of a food supply.


## Status
<img src="https://img.shields.io/static/v1?label=AlgaFood-API&message=Concluido&color=green" />


## Features
- [x] List, find, create, update and remove cities
- [x] List, find, create, update and remove cuisines
- [x] List, find, create, update and remove states
- [x] List statistics, generate pdf of daily sales
- [x] List, find, create, update and remove methods of payment
- [x] List, find, create, update, remove, list permissions, associate permissions and disassociate permissions from groups
- [x] List, find, confirm, cancel, deliver, create orders
- [x] List permissions
- [x] List, find, create, update and remove products
- [x] List, find, create, update, remove, active, deactivate, list methods of payment, associate methods of payment, disassociate methods of payment, list products, add product, update product, open, close, list users, associate user, desassociate user, update product image, find product image, remove product image from restaurant
- [x] List, find, create, update, remove, update password, find groups, associate groups and disassociate groups from user
- [x] Generate and check token
- [x] Documentação swagger
- [x] API root entry point 


## Installation
These instructions will get you a copy of the full project up and running on your local machine for development and testing purposes.
I recommend use [Docker](https://docs.docker.com/engine/install) to install and run the databases above.

- To download the project follow the instructions bellow:

    * clone the project to your machine

      ```sh 
      git clone git@github.com:titiscms/algafood-api.git api
      ```

    * import the project into the eclipse or spring tool suite

    * creating mysql container

      ```sh 
      docker container run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root --name algafood-mysql mysql:8.0 
      ```
      
    * running up the application
      
      > set up the profile `development` in your IDE, then you will be able to test the application.
      
<br />

- Also, I'm sending the Postman file for call the API, download it [here](https://raw.githubusercontent.com/titiscms/titiscms.github.io/master/assets/AlgafoodAPI.postman_collection.json).

## Screenshot
- Root entry point
![alt text](https://raw.githubusercontent.com/titiscms/titiscms.github.io/master/assets/api.png)

- Documentation
![alt text](https://raw.githubusercontent.com/titiscms/titiscms.github.io/master/assets/documentation.png)


## Technologies
![java-image] ![boot-image] ![security-image] ![mysql-image] ![flyway-image] ![jasper-image] ![swagger-image] ![thymeleaf-image] ![redis-image] ![docker-image]  ![loggly-image]


[java-image]: https://img.shields.io/static/v1?label=Java&message=11&color=green
[boot-image]: https://img.shields.io/static/v1?label=SpringBoot&message=2.2.2&color=green
[security-image]: https://img.shields.io/static/v1?label=SpringSecurityOAuth&message=2.3.8&color=green
[mysql-image]: https://img.shields.io/static/v1?label=MySQL&message=8.0&color=red
[flyway-image]: https://img.shields.io/static/v1?label=Flyway&message=6.0.8&color=red
[jasper-image]: https://img.shields.io/static/v1?label=JaperReport&message=6.13.0&color=blue
[aws-image]: https://img.shields.io/static/v1?label=AWS&message=Services&color=yellow
[swagger-image]: https://img.shields.io/static/v1?label=SpringFoxSwagger&message=2.9.2&color=orange
[redis-image]: https://img.shields.io/static/v1?label=Redis&message=6.2.1-alpine&color=red
[docker-image]: https://img.shields.io/static/v1?label=Docker&message=1.4.13&color=blue
[thymeleaf-image]: https://img.shields.io/static/v1?label=Thymeleaf&message=2.2.2&color=green
[loggly-image]: https://img.shields.io/static/v1?label=Loggly&message=0.1.5&color=yellow













