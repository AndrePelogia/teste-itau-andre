Spring Boot Application

Esta é uma aplicação Spring Boot desenvolvida com Java 21, Maven e H2. A aplicação demonstra funcionalidades básicas de um sistema de gerenciamento de clientes e transferências.

Visão Geral
Esta aplicação é uma API RESTful que permite a criação e listagem de clientes e a realização de transferências entre contas. Utiliza o banco de dados H2 em memória para fins de desenvolvimento e testes.

Tecnologias
Java 21: Versão mais recente do Java, proporcionando suporte a recursos modernos da linguagem.
Spring Boot: Framework para desenvolvimento de aplicações Java baseadas em Spring.
Maven: Ferramenta de gerenciamento de projetos e construção.
H2: Banco de dados em memória, ideal para desenvolvimento e testes.

Configuração
Pré-requisitos
Certifique-se de que você tenha o JDK 21 e o Maven instalados. Você pode verificar suas versões com os seguintes comandos:
java -version
mvn -version

Clonando o Repositório
Clone o repositório para o seu ambiente local:
git clone https://github.com/AndrePelogia/teste-itau-andre.git
cd teste-itau-andre
git checkout master

Configuração do Maven
Compile e construa o projeto com Maven:
mvn clean install

Configuração do Banco de Dados
A configuração do banco de dados H2 está incluída no arquivo application.properties. O banco de dados H2 é configurado para ser executado em memória e acessível em http://localhost:8080/h2-console
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

Execução
Para executar a aplicação, utilize o comando Maven:
mvn spring-boot:run

A aplicação será iniciada em http://localhost:8080.

Documentação da API pode ser visualizada após subir a aplicação em http://localhost:8080/swagger-ui/index.html#/
