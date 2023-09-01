
<h1 align="center">Spring Boot Template Project</h1>
<div align="center">

[![Status](https://img.shields.io/badge/status-finished-success.svg)]()

</div>

## 📝 Table of Contents

- [📝 Table of Contents](#-table-of-contents)
- [🧐 About <a name = "about"></a>](#-about-)
- [🔑 Prerequisites <a name = "prerequisites"></a>](#-prerequisites-)
- [⛏️ Built Using <a name = "built_using"></a>](#️-built-using-)
- [✍️ Authors <a name = "authors"></a>](#️-authors-)

## 🧐 About <a name = "about"></a>

Welcome to the Spring Boot Template Project! This repository serves as a versatile starting point for your Java web application development needs. Our project is designed to provide you with all the fundamental components required for building robust Spring Boot applications. Here's a brief overview of what has beed included in this template:

* Thymeleaf Admin Panel: We've included a basic admin panel built with Thymeleaf and tailwind css. This panel is ready for customization and expansion to manage various aspects of your application.
* Exception Handling
* Account Handling: We've included JWT based authentication and role-based security in this project.
* Internationalization: We've included internationalization support to make it easier to create multilingual applications.
* Tests: The template project includes a testing framework to help you ensure the reliability and stability of your application through unit and integration tests. Our example tests include in-memory database support, as well as integration for spring-boot test framework.
* Profiles for Local Development and Production: Configure your project for local development and production environments with profiles that streamline setup and deployment.
* Docker Support: We've provided scripts, that will build and deploy your application to your local docker instance.

## 🔑 Prerequisites <a name = "prerequisites"></a>

* Java 17
* Apache Maven
* Node.js

### Optional Prerequisites <a name = "prerequisites"></a>

* Docker Desktop

In order to run the application you will need to configure the application.properties file in the resources folder with the file structure shown in the example below:

```
account.confirmation.jwt.secret=TODO
unlock.by.mail.confirmation.jwt.secret=TODO

(...)

mail.smtp.ssl.trust=TODO
```

You will also need to edit the JDBCConfig.java file found in the config package by supplying it with the correct url's and passwords for the data sources. An example datasource is shown below:

```
@DataSourceDefinition(
        name = "java:app/jdbc/ssbd01mok",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "ssbd01mok",
        password = "TODO",
        serverName = "TODO",
        portNumber = 5432,
        databaseName = "ssbd01",
        transactional = true,
        initialPoolSize = 1,
        minPoolSize = 0,
        maxPoolSize = 32,
        isolationLevel = Connection.TRANSACTION_READ_COMMITTED)
```

Use the sql scripts found in resources/META-INF/sql to populate your database with sample data.

## ⛏️ Built Using <a name = "built_using"></a>

- [Java](www.java.com)
- [Apache Maven](www.maven.apache.org)
- [JavaEE 8](https://www.oracle.com/java/technologies/java-ee-8.html)
- [React.js](https://reactjs.org/)
- [PostgreSQL](https://www.postgresql.org/)
- [Hibernate](https://hibernate.org/)

## ✍️ Authors <a name = "authors"></a>

* [Przemysław Zdrzalik](https://github.com/ZdrzalikPrzemyslaw)
* [Julia Szymańska](https://github.com/JuliaSzymanska)
* [Grzegorz Muszyński](https://github.com/szerszen199)
* [Witold Pietrzak](https://github.com/WitoldPietrzak)
* [Piotr Antczak](https://github.com/pantczak)
* Bartłomiej Graczyk
* Tomasz Woźniak
