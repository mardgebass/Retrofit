Две группы тестов:
1. Для категорий;
2. Для продукции.

Для категорий один тест с запросом GET, где есть позитивнаяи негативная проверка.
Для продукции CRUD:
Create - ProductPostTest;
Read - ProductGetTest(продукт id), ProductsGetTest(список продуктов);
Update - ProductUpdateTest;
Delete - ProductDeleteTest.
В некоторых тестах для продукции есть негативные проверки.
