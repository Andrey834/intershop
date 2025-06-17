# Intershop (Интернет-магазин)

## 

* Frontend – Thymeleaf
* Backend – Java 21, Spring Boot 3.5
* Database – Postgresql, Redis, Liquibase.
* Test - JUnit 5, TestContainers(Postgresql, Redis)

## Installation guide

Set environments

* IS_PORT -> Порт приложения
* IS_PG_DB -> Имя базы данных PostgreSQL
* IS_PG_PORT -> Порт базы данных PostgreSQL
* IS_PG_USER -> Имя пользователя базы данных PostgreSQL
* IS_PG_PASSWORD -> Пароль пользователя базы данных PostgreSQL
* IS_REDIS_PORT -> Порт Redis

## Запуск приложения

Install Docker with Compose:

```bash
docker compose up -d --build
```
