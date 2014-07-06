
Exercise 6
==========

Have a look at the project<br/>
You should be familiar with the following packages (and there is no need to touch them in the exercise)
- dao: Not changed
- model: Not changed
- util<br/>
    Contains the a solution for loading users and books from exercise 4<br/>
    Also contains the BookAnalyzer we had in the previous exercises
- service: Contains the Library service
- application: Contains AppConfig for spring configurations

There is a new controller package for this exercise
- controller<br/>
    Contains spring controller that is half baked and is up to you to continue cooking<br/>
    ControllerConfig class inside config directory for controller configurations

1. Have a quick look at the following classes (don't do anything yet)
  - LibraryController (controller package)
  - LibraryControllerTest (test directory)
  - LibraryControllerRemoteTest (test directory inside remote directory)

2. Configurations:
  1. Complete the webapp/WEB-INF/web.xml configuration
    - Configure the ROOT application context to load all beans EXCEPT the controllers (use AppConfig).
    - Configure the Library Servlet application context to load ONLY the controller beans! (use ControllerConfig)
  2. Complete the ControllerConfig, don't forget to enable spring web mvc

3. Complete the LibraryController (all the TODOs in the file)
  - Add proper spring bean stereotype
  - Add url mapping so all the methods in this controller urls will start with "/books"<br/>
    At this stage, you should be able to run the LibraryControllerTest.testCreateBook and LibraryControllerTest.testReadAllBooks tests 
    <br/>BUT before you can execute them you need to fix LibraryControllerTest ContextConfiguration and add appropriate configuration classes<br/>
  - Complete delete book method (add appropriate annotations)
  - Complete readBooksByAuthor book method (add appropriate annotations)<br/>
    NOTICE that the author is passed via url param<br/>
       for example the url to this method might be: http://localhost:8080/books?author=Lewis Carroll<br/>
  - Make sure that all of the LibraryControllerTest pass

4. Complete the LibraryControllerRemoteTest happy flow test (all the TODOs in the file)<br/>
   DO NOT EDIT THE testHappyFlow METHOD!<br/>
   ONLY EDIT THE UNIMPLEMENTED METHODS!!!<br/>
    - Complete createBook book method
    - Complete readBooksByAuthor book method<br/>
    (don't forget to remove the @Ignore annotation in order to enable running the test)<br/>
    (You should have a tomcat running in order to run the test, make sure the "baseUrl" is configured to your tomcat host:port)

###N O T I C E
- [ ] \(again\) DO NOT EDIT any of the files in the directories: dao, model, service, util, application
- [ ] \(or in other words\) ONLY EDIT the files in the controller package
- [ ] Notice the requirements for web.xml configuration
- [ ] In remote tests, make sure you comment the @Ignore in order for the test to run
- [ ] In remote tests, make sure the "baseUrl" is configured to your tomcat host:port (the current default is http://localhost:8080/)
- [ ] "java.lang.ClassNotFoundException: java.lang.annotation.Repeatable" - IGNORE THIS EXCEPTION IF YOU SEE IT IN THE LOG FILE

###How to configure tomcat (On intelliJ IDEA)?
1. Run -> Edit Configurations...
2. Press the green plus (+) sign, hover over "tomcat server" press "local"
3. In the server tab:
  - Un-check the start browser
  - If you wish to change the tomcat port then do so in the "HTTP port" (for example, change to 9090)
4. In the Deployment tab:
  - Press the green plus (+) sign on the right, press artifact and choose "exercise6:war exploded"
5. DONE
