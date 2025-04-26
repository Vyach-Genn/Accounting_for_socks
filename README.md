🧦 Accounting for Socks
Приложение для учета носков на складе магазина через HTTP API.

📋 Описание
Это простое Java-приложение на Spring Boot, которое позволяет:

📥 Регистрировать приход носков

📤 Регистрировать отпуск носков

🔍 Получать информацию о количестве носков по заданным параметрам

Работает с базой данных PostgreSQL и использует Liquibase для версионирования схемы.

🚀 Технологии проекта
Java 17+

Spring Boot

Spring Web

Spring Data JPA

PostgreSQL

Liquibase

Docker (опционально для базы данных)

✨ Автор
Разработчик: Вячеслав Краснов

Почта: vachgen@jmal.com

⚙️ Как запустить проект
1. Клонировать репозиторий
   git clone https://github.com/your-username/accounting-for-socks.git
   cd accounting-for-socks
2. Настроить базу данных
   Убедитесь, что у вас установлен PostgreSQL (или другая реляционная СУБД).

Откройте файл src/main/resources/application.properties и пропишите настройки подключения к базе данных:

properties
spring.datasource.url=jdbc:postgresql://localhost:5432/dbForTest
spring.datasource.username=UserForTest
spring.datasource.password=123
⚡ При запуске приложения миграции базы данных будут выполнены автоматически с помощью Liquibase. Если база данных пуста, она будет создана.

3. Сборка и запуск приложения
   Собрать и запустить приложение можно с помощью Maven:

bash
./mvnw spring-boot:run
После успешного запуска приложение будет доступно по адресу:

arduino
http://localhost:8080
📚 API
1. Регистрация прихода носков на склад
   Метод: POST

URL: /api/socks/income

Пример запроса:

json
{
"color": "black",
"cottonPart": 80,
"quantity": 10
}
Ответы:

200 OK — Носки успешно добавлены.

400 Bad Request — Неправильный формат данных или отсутствуют обязательные параметры.

500 Internal Server Error — Внутренняя ошибка сервера.

2. Регистрация отпуска носков со склада
   Метод: POST

URL: /api/socks/outcome

Пример запроса:

json
{
"color": "black",
"cottonPart": 80,
"quantity": 5
}
Ответы:

200 OK — Носки успешно списаны со склада.

400 Bad Request — Неправильный формат данных или отсутствуют обязательные параметры.

500 Internal Server Error — Внутренняя ошибка сервера.

3. Получение общего количества носков на складе
   Метод: GET

URL: /api/socks

Параметры запроса:


Параметр	Описание
color	Цвет носков (строка)
operation	Операция сравнения (moreThan, lessThan, equal)
cottonPart	Процент хлопка в составе носков (от 0 до 100)
Пример запроса:

http
GET /api/socks?color=black&operation=moreThan&cottonPart=70
Ответы:

200 OK — Возвращается общее количество носков, удовлетворяющих условиям.

400 Bad Request — Неправильный формат данных или отсутствуют обязательные параметры.

500 Internal Server Error — Внутренняя ошибка сервера.

🧪 Тестирование
Проект тестируется с помощью JUnit 5 и Mockito.

Запуск всех тестов:

bash
./mvnw test
⚡ Дополнительные функции
Используется Liquibase для миграций базы данных.

Приложение можно развернуть в облаке (например, Heroku или AWS).

📄 Лицензия
Этот проект лицензируется под лицензией MIT. См. файл LICENSE для подробностей.

🐳 Работа с Docker (опционально)
Если хотите развернуть базу данных через Docker, используйте следующий docker-compose.yml:

yaml
version: '3.1'

services:
db:
image: postgres:latest
container_name: socks_db
environment:
POSTGRES_USER: UserForTest
POSTGRES_PASSWORD: 123
POSTGRES_DB: dbForTest
ports:
- "5432:5432"
networks:
- socks_network

networks:
socks_network:
driver: bridge
Запустите контейнеры:

bash
docker-compose up -d