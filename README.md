# Movie Search Service
## Проект по дисциплине ПНАЯВУ
Сервис поиска данных о фильме. <br>
Проект студента группы **250503 Николаева Ивана**.

---
## Технологии и зависимости
+ Язык программирования Java
+ Фреймворк Spring
---
## Условия лабораторных ##
1. Создать и запустить локально простейший веб/REST сервис, используя любой открытый пример с использованием Java stack: Spring (Spring Boot)/maven/gradle/Jersey/ Spring MVC. <br> Добавить GET ендпоинт, принимающий входные параметры в качестве queryParams в URL согласно варианту, и возвращающий любой hard-coded результат в виде JSON согласно варианту."
2. Подключить в проект БД (PostgreSQL/MySQL/и т.д.).
   Реализация связи один ко многим @OneToMany. Реализация связи многие ко многим @ManyToMany. Реализовать CRUD-операции со всеми сущностями.
3. Добавить в проект GET ендпоинт (useful) с параметром(-ами). Данные должны быть получены из БД с помощью ""кастомного"" запроса (@Query) с параметром(-ами) ко вложенной сущности. Добавить простейший кэш в виде in-memory Map (в виде отдельного бина).
---
## SonarCloud
[Качество кода](https://sonarcloud.io/summary/overall?id=ranty97_MovieSearchService)
