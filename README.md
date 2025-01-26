<h1>ExchangeRatesBot</h1>

### <a name="Стек_технологий">Стек технологий</a>
- Java
- Spring Boot
- Maven
- Telegram API

### <a name="О_проекте">О проекте</a>
  Telegram-бот для получения актуальных курсов валют доллара и евро напрямую с сайта Центрального Банка РФ.

### <a name="Запуск_проекта">Запуск проекта</a>
- Склонируйте репозиторий ```git clone https://github.com/ZaharovJaroslav/ExchangeRatesBot.git```
- Перейдите в директорию проекта ```cd ExchangeRatesBot```
- Настройте токен Telegram-бота в файле resources.application.yaml
- Создайте исполняемый jar ```mvn clean package```
- Запустите исполняемый jar ```java -jar target/ExchangeRatesBot-1.0-SNAPSHOT.jar```

