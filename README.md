# OnlineBanking
Spring Boot CRUD app

Install PostgreSQL and create a database and a user 

```
sudo -U username psql
CREATE DATABASE onlinebanking;
CREATE USER "user" WITH password 'password';
GRANT ALL PRIVILEGES ON DATABASE onlinebanking TO "user";
```