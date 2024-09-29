# Job4j_urlShortcut
![Java CI](https://github.com/Yurchenkopi/job4j_url_shortcut/actions/workflows/main.yml/badge.svg)
![Coverage](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/Yurchenkopi/job4j_url_shortcut/master/.github/badges/jacoco.json)
![Branches](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/Yurchenkopi/job4j_url_shortcut/master/.github/badges/branches.json)
## Описание проекта
Проект выполнен в учебных целях для освоения RestFul API архитектуры.
Проект является сервисом, меняющий ссылки на зарегистрированный в системе сайт уникальным кодом.
Сервис предоставляет следующий функционал:
+ Регистрация сайта. Перед использования сервиса, необходимо отправить запрос {POST /registration}, в ответ на который вернется статус регистрации и пара логин и пароль, уникальные для каждого зарегистрированного ресурса.
+ Авторизация пользователя, реализованая через JWT.
+ Регистрация URL: возможность предоставляется после регистрации сайта в системе.
+ Переадресацию на зарегистрированный URL: выполняется без авторизации.
+ Сбор и предоставление статистики: считается количество вызовов каждого адреса.
***
## Стек технологий
- Java 17
- PostgreSQL 14
- Maven 3.8.5
- Spring boot 2.7.6
- Liquibase 4.15.0
***
## Требования к окружению
- Java 17
- PostgreSQL 14
- Maven 3.8.5
