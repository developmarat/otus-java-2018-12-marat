Дз для курса "Разработчик Java" в OTUS
========================================
Группа 2018-12
-----------------------------------------
### Преподаватели:
Виталий Чибриков

Сергей Петрелевич

### Студент:
Марат Нугуманов 

develop.marat@gmail.com
***


**ДЗ 01. Сборка и запуск проекта**  
Создать проект под управлением maven в Intellij IDEA.   
Добавить зависимость на Google Guava/Apache Commons/библиотеку на ваш выбор.  
Использовать библиотечные классы для обработки входных данных.  
Задать имя проекта (project_name) в pom.xml   
Собрать project_name.jar содержащий все зависимости.  
Проверить, что приложение можно запустить из командной строки.  

**ДЗ 02. Измерение памяти**
Напишите стенд для определения размера объекта.   
Передавайте для измерения в стенд фабрику объектов.  
Определите размер пустой строки и пустых контейнеров. Определите рост размера контейнера от количества элементов в нем.  

**ДЗ 03. MyArrayList**  
Написать свою реализацию ArrayList на основе массива.  
class MyArrayList<T> implements List<T>{...}  
Проверить, что на ней работают методы   
addAll(Collection<? super T> c, T... elements)  
static <T> void	copy(List<? super T> dest, List<? extends T> src)  
static <T> void	sort(List<T> list, Comparator<? super T> c)  
из java.util.Collections  

**ДЗ 04. Измерение активности GC**  
Написать приложение, которое следит за сборками мусора и пишет в лог количество сборок каждого типа (young, old) и время которое ушло на сборки в минуту.  
Добиться OutOfMemory в этом приложении через медленное подтекание по памяти (например добавлять элементы в List и удалять только половину).  
Настроить приложение (можно добавлять Thread.sleep(...)) так чтобы оно падало с OOM примерно через 5 минут после начала работы.  
Собрать статистику (количество сборок, время на сборрки) по разным типам GC.   
Сделать выводы.  

**ДЗ 05. Тестовый фреймворк на аннотациях**  
Написать свой тестовый фреймворк.   
Поддержать свои аннотации @Test, @Before, @After.   
Запускать вызовом статического метода с именем класса с тестами.  

**ДЗ 06. my cache engine**  
Напишите свой cache engine с soft references.  

**ДЗ 07. Написать эмулятор АТМ**  
Написать эмулятор АТМ (банкомата).  
Объект класса АТМ должен уметь
* принимать банкноты разных номиналов (на каждый номинал должна быть своя ячейка)  
* выдавать запрошенную сумму минимальным количеством банкнот или ошибку если сумму нельзя выдать  
* выдавать сумму остатка денежных средств  

**ДЗ 08. ATM Department**  
Написать приложение ATM Department:
* Приложение может содержать несколько ATM  
* Departmant может собирать сумму остатков со всех ATM  
* Department может инициировать событие – восстановить состояние всех ATM до начального.  
(начальные состояния у разных ATM могут быть разными)  

**ДЗ 09. JSON object writer**  
Напишите свой json object writer (object to JSON string) аналогичный gson на основе javax.json или simple-json и Reflection.  
Поддержите массивы объектов и примитивных типов, и коллекции из стандартный библиотерки.  

**ДЗ 10. myORM**  
Создайте в базе таблицу с полями:
* id bigint(20) NOT NULL auto_increment   
* name varchar(255)  
* age int(3)  
Создайте абстрактный класс DataSet. Поместите long id в DataSet.   
Добавьте класс UserDataSet (с полями, которые соответствуют таблице) унаследуйте его от DataSet.   
Напишите Executor, который сохраняет наследников DataSet в базу и читает их из базы по id и классу.   
<T extends DataSet> void save(T user){…}  
<T extends DataSet> T load(long id, Class<T> clazz){…}  
Проверьте его работу на UserDataSet  


**ДЗ 11. Hibernate ORM**  
На основе ДЗ 10:
1. Оформить решение в виде DBService (interface DBService, class DBServiceImpl, UsersDAO, UsersDataSet, Executor)
2. Не меняя интерфейс DBSerivice сделать DBServiceHibernateImpl на Hibernate.
3. Добавить в UsersDataSet поля:
адресс (OneToOne)   
    class AddressDataSet{   
		private String street;   
    }   
и телефон* (OneToMany)  
    class PhoneDataSet{  
		private String number;  
    }  
Добавить соответствущие датасеты и DAO.   


**ДЗ 12. Веб сервер**  
Встроить веб сервер в приложение из ДЗ-11.   
Сделать админскую страницу, на которой можно добавить пользователя, получить имя пользователя по id и получить количество пользователей в базе.  

**ДЗ 13. Многопоточная сортировка**  
Написать приложение, которое сортирует массив чисел в 4 потоках с использованием библиотеки или без нее.  

**ДЗ 14. MessageSystem**  
Добавить систему обмена сообщениями в веб сервер из ДЗ-12.   
Организовать структуру пакетов без циклических зависимостей.  

**ДЗ 15. WAR**  
Собрать war для приложения из ДЗ-12 или ДЗ-14.   
Создавать кэш и DBService как Spring beans, передавать (inject) их в сервлеты.   
Запустить веб приложение во внешнем веб сервере.  

**ДЗ 16. MessageServer**  
Cревер из ДЗ-15 разделить на три приложения:
* MessageServer
* Frontend
* DBServer  
Запускать Frontend и DBServer из MessageServer.  
Сделать MessageServer сокет-сервером, Frontend и DBServer клиентами.  
Пересылать сообщения с Frontend на DBService через MessageServer.   
Запустить приложение с двумя фронтендами (на разных портах)* и двумя датабазными серверами.  
