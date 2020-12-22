## API DEMO PROJECT | RESTful Api
 RESTful API for collecting university and student information.<br>
 (This repository is being developed to database connecting)
 
### Technology Stacks
* Java 11
* Spring Boot 2.4
* Maven
### API Testing
* Postman
### Installation
* Ensure that Java 8 and Maven 3.6 are installed
* Clone this repository : ```git clone https://github.com/cyberbliss/springboot-rest-example.git```

## Usage
### Running the Spring Boot app
* Navigate to the directory into which you cloned the repository and execute this:``` mvn spring-boot:run``` <br>
* Once started you can access the APIs on port 4000, e.g. ```http://localhost:4000/universitied```<br>
* The port number can be changed by editing the port property in ```demo/src/main/resources/application.properties```<br>

## RESTful API Endpoints
All inputs and outputs use JSON format.
```

/universitiesall
  GET / - List of university data

/universities
  GET / - List of university name
  GET /{id} - View university data as well as List of all student name
  POST / - Add university - required : int id , String name , String name_init
  PUT /{id} - Edit university data - required : String name , String name_init
  DELETE /{id} - Delete university

/studentsall
  GET / - List of student data

/students
  GET / - List of student name
  GET /{id} - View student data 
  POST / - Add student - required : int id , String name , List<Education> education
  PUT /{id} - Edit student data - required : String name , List<Education> education
  DELETE /{id} - Delete student
  
  ```
  
  ## JSON format
  
* Class : Student <br>
```JSON
   {
    "id": int,
    "name": String,
    "education": Object[]
  }
```
* Class : Education
```JSON
  {
    "degree": String,
    "uName": String
  }
```
* Class : UniversityInfo <br>
```JSON
   {
    "id": int,
    "name": String,
    "name_std": String[],
    "nameInit": String
   }
```

### Add sample data 
Add information by editing it in the DataController.java 
```Java
public DataController() {

        // Sample Data
        //universities.add(new UniversityInfo(int id, String name, String name_init));
        universities.add(new UniversityInfo(counter_university.getAndIncrement(), "Mahidol University", "MU"));
        
        //educations.add(new Education(String degree, String uName)); 
        // String uName get method universities.get(index).getName()
        educations.add(new Education("Bachelor's Degree", universities.get(0).getName()));
    
        //students.add(new Student(int id,String name, List<Education> education)); 
        // List<Education> education get educations
        students.add(new Student(counter_student.getAndIncrement(), "Parichaya", educations));
        MapStudentUniversity();
    }
```
