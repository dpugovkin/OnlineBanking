# OnlineBanking
Spring Boot CRUD app

Install PostgreSQL and create a database and a user 

```
sudo -U "username" psql
CREATE DATABASE onlinebanking;
CREATE USER "user" WITH password 'password';
GRANT ALL PRIVILEGES ON DATABASE onlinebanking TO "user";
```

Install maven and run in project folder

```
mvn spring-boot:run
```

After you can browse app by `http://localhost:8080`. Before project running make sure port is free.

Working features:

```
+ entity persistence.
+ views: client, clients, account, accounts, transaction, transactions.
+ form addition: client, account.
```

TODO:

```
- balance verifying before transaction.
- hibernate locks on balance.
- form addition: transaction.
- transaction filtering.
- additional features.
```