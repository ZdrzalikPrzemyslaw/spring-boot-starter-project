
<h1 align="center">Spring Boot Template Project</h1>
<div align="center">


[![Status](https://img.shields.io/badge/status-finished-success.svg)]()

</div>

## 📝 Table of Contents <a name = "table-of-contents"></a>

- [📝 Table of Contents](#table-of-contents)
- [🧐 About](#about)
- [🔑 Prerequisites](#prerequisites)
- [🚀 How to Run](#how-to-run)
- [⛏️ Built Using](#built-using)
- [✍️ Author](#authors)

## 🧐 About <a name = "about"></a>

Welcome to the Spring Boot Template Project! This repository serves as a versatile starting point for your Java web application development needs. Our project is designed to provide you with all the fundamental components required for building robust Spring Boot applications. Here's a brief overview of what has beed included in this template:

* Thymeleaf Admin Panel: We've included a basic admin panel built with Thymeleaf and tailwind css. This panel is ready for customization and expansion to manage various aspects of your application.
* Exception Handling
* Account Handling: We've included JWT based authentication and role-based security in this project.
* Internationalization: We've included internationalization support to make it easier to create multilingual applications.
* Tests: The template project includes a testing framework to help you ensure the reliability and stability of your application through unit and integration tests. Our example tests include in-memory database support, as well as integration for spring-boot test framework.
* Profiles for Local Development and Production: Configure your project for local development and production environments with profiles that streamline setup and deployment.
* Docker Support: We've provided scripts, that will build and deploy your application to your local docker instance.
* Database versioning: we included a starting point for database schema using liquibase

## 🔑 Prerequisites <a name = "prerequisites"></a>

* [Java 17](www.java.com)
* [Apache Maven](www.maven.apache.org)
* [Node.js](https://nodejs.org/en)

### Optional Prerequisites <a name = "prerequisites"></a>

* [Docker](https://www.docker.com)


## 🚀 How to Run <a name = "how-to-run"></a>

We've included multiple useful .sh scripts that will help you run the application. They are located in the [scripts](scripts) directory.

First, start by getting the [database](https://github.com/ZdrzalikPrzemyslaw/PostgreSQL-docker) running. 

You can then populate it using the [correct script - liquibase_dev.sh](scripts/liquibase_dev.sh).

After that, you can run the app by simply executing the [correct script - local_clean_run.sh](scripts/local_clean_run)

Another way to get the app running is using inteliJ. Simply recreate the run cofiguration seen in the image below:
<p align="center">
    <img src="/.github/run_configuration.png" />
</p>

### Local dockerized deployment

Simply exectute the [correct script - rebuild-docker.sh](docker/rebuild-docker.sh)


### Running tests

Simply execute the [correct script - run_test.sh](scripts/run_tests.sh).

### Hot reload CSS for local development

Simply execute the [correct script - hot_reload_styles.sh](scripts/hot_reload_styles.sh)


## ⛏️ Built Using <a name = "built_using"></a>

- [Java](www.java.com)
- [Apache Maven](www.maven.apache.org)
- [Spring Boot 2](https://spring.io/projects/spring-boot)
- [Liquibase](https://www.liquibase.org)
- [Docker](https://www.docker.com)
- [JUnit](https://junit.org/junit5/)
- [Hibernate](https://hibernate.org/)
- [Node.js](https://nodejs.org/en)
- [Thymeleaf](https://www.thymeleaf.org)
- [Tailwind](https://tailwindcss.com)

## ✍️ Authors <a name = "authors"></a>

* [Przemysław Zdrzalik](https://github.com/ZdrzalikPrzemyslaw)
* [Julia Szymańska](https://github.com/JuliaSzymanska)
* [Witold Pietrzak](https://github.com/WitoldPietrzak)

