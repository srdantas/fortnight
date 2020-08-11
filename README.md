# Fortnight
Simple Core Banking for basic operations, like movements between accounts and tax withdraw

![Java CI with Gradle](https://github.com/srdantas/fortnight/workflows/Java%20CI%20with%20Gradle/badge.svg)
___

### Operations
For using operations, need accounts for it, the API provides a endpoint for create, with name and document (like CPF).

- Deposit in exists a customer
- Withdraw money
- Transfer Money between accounts

### Operations rules
- When withdraw money from a customer, 1% of value is charged
- When deposit money into a customer, balance received 0.5% of value

*Transfer between accounts is unlimited and free*

## Run
For run app, build application
```shell script
app ~ ./gradlew clean build
```

build a docker image locally:
```shell script
app ~ docker build -t fortnight .
```

after build, you can run with network mode:
```shell script
docker run --net=host fortnight
```

Fortunately, this project have a docker compose configure for run dependencies:
```shell script
~ docker-compose up
```

## bdds
The folder bdds have a cucumber tests, write with python (behave), for all features exists on this project.
This is a most high level of tests of this app.

Into bdds exists a config file, called `behave.ini`, that have a server url config, default value is `localhost:8080`.

for run features (before run, you need start the application and run it locally):
```shell
cd bdds && \ 
pip install . && \ 
behave
```
*run features required python 3.\* and pip installed*

This tests, also generate a good doc, are very important for understand domains of this project and what are responsibility. 
## app
This is a java application.

Are a reactive project with spring webflux and have a mysql database for data persists.
An important decision for not using r2dbc, reactive api for relational databases, are because this lib haven't a support for relationship between tables.

Packages are using basic clean architecture, this are proposes by uncle bob. 
Have one public use case by a feature, withdraw, deposit and transfer.

Database model is so simple, for make more fast and easy for understand and read code. When make a good database model for this problem are so big and complex.

Tests into an app are two: unit tests and integration tests.
- unit tests are used for test use cases, gateways and adapter
- integration tests setup a spring context, with h2 for data base, and make requests using endpoints.

#### bonus and tax
For a bonus and tax into application (deposit and withdraw, respectively) required update values into `application.yml`.
- `operations.debit.bonus` are used for calculate a bonus in a deposit. This value is percentage.
- `operations.withdraw.tax` are used for calculate a tax in withdraw. This value is percentage.    