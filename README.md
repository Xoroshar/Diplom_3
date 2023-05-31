<br />
<div align="left">


<h3 align="center">Диплом. Задание 3: веб-приложение</h3>

В данном проекте представлен код для автоматизированного тестирования ротестировать веб-приложение
[Stellar Burgers](https://stellarburgers.nomoreparties.site/). Автотесты покрывают следующие тесты:

* Регистрация.
* * Успешная регистрация.
* * Ошибка для некорректного пароля.

* Вход.
* * Вход по кнопке «Войти в аккаунт» на главной.
* * Вход через кнопку «Личный кабинет».
* * Вход через кнопку в форме регистрации.
* * Вход через кнопку в форме восстановления пароля.

* Переход в личный кабинет.
* * Переход по клику на «Личный кабинет».
* * Переход из личного кабинета в конструктор
* * Переход по клику на «Конструктор» и на логотип Stellar Burgers.

* Выход из аккаунта
* * Выход по кнопке «Выйти» в личном кабинете.

* Раздел «Конструктор»
* * Переходы к разделу:«Булки».
* * Переходы к разделу: «Соусы».
* * Переходы к разделу: «Начинки».

Использованный стек технологий:  Java 11, JUnit 4.13.2, rest-assured 5.3.0. С результатами тестирования можно
ознакомиться в отчете Allure.

Интсрукция по запуску:

*mvn clean test*

для генерации отчета Allure нужно дополнительно выполнить команду:

*mvn allure:serve*

</div>

