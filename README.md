## API PROJECT | RESTful Api
 RESTful API for collecting university and student data.<br>
 (This repository is being developed to database connecting)
 
### Technology Stacks
* Java 11
* Spring Boot 2.4
* Maven 3.6
### API Testing
* Postman
### Installation
* Ensure that Java 11 and Maven 3.6 are installed
* Clone this repository : ```git clone https://github.com/filmptz/RESTful-API.git```

## Usage
### Running the Spring Boot app
* Navigate to the directory into which you cloned the repository and execute this: ```mvn spring-boot:run``` <br>
* Once started you can access the APIs on port 4000, e.g. ```http://localhost:4000/universities```<br>
* The port number can be changed by editing the port property in ```src/main/resources/application.properties```<br>

## RESTful API Endpoints
All inputs and outputs use JSON format.
```

/universitiesall
  GET    /universitiesall     - List of university data

/universities
  GET    /universities        - List of university name
  GET    /universities/{id}   - View university data as well as List of all student name
  POST   /universities        - Add university       -> required : String name , String name_init
  PUT    /universities/{id}   - Edit university data -> required : String name , String name_init
  DELETE /universities/{id}   - Delete university

/studentsall
  GET    /studentsall         - List of student data

/students
  GET    /students            - List of student name
  GET    /students/{id}       - View student data 
  POST   /students/students   - Add student         -> required : String name , List<Education> education
  PUT    /students/{id}       - Edit student data   -> required : String name , List<Education> education
  DELETE /students/{id}       - Delete student
  
  ```
  ### Guide : POST | PUT  method
  
 JSON format for POST and PUT Method
  
  ```JSON
  /universities || /universities/{id}
  {
        "id": int,
        "name": String,
        "name_init": String
  }
  
  /students || /students/{id} 
   {
        "id": int,
        "name": String,
        "education": [
            {
                "degree": String,
                "uName": String
            }
        ]
    }
  
  ```
  ### Exception Handling
  The function will return the correct conversion if the supplied problems are properly formatted, otherwise, it will **return a string** that describes an error that is meaningful to the user.
* Situations that will return an error:
     * If the requesting **id** do not has in the data will return:<br> ```Could not find data id :: {id}```
     * Each editing or adding to the **university data** should not revise the university name to the name already in the university data. Otherwise, the function will return:<br> ```Could not created the data :: Already has this university name```
     * Each editing or adding **the name of the university in the student data**. The university name should be match in university name of university data. Otherwise, the function will return:<br> ```Could not created the data :: Can not found this university name in UniversityInfo.```
* Handling Http errors:
     * **404: Not Found** will return: ```Could not find data id :: {id}```
     * **500: Internal Server Error** will return: ```Internal Server Error :: Format is invalid.```
      
  ### JSON format  
* Class : Student 
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
* Class : UniversityInfo 
```JSON
   {
    "id": int,
    "name": String,
    "name_std": String[],
    "name_init": String
   }
```
## Sample data
```
1.
Student name : Parichaya Thanawuthikrai 
Education : Bachelor's Degree, Mahidol University
            Master's Degree, Thammasat University
2.
Student name : Sathinee Thanawuthikrai 
Education : Bachelor's Degree, Mahidol University
 
```
### Add sample data 
Add data by editing it in the DataController.java 
```Java
public DataController() {

        // Sample Data
        //UniversityInfo
        //universities.add(new UniversityInfo(int id, String name, String name_init));
        universities.add(new UniversityInfo(counter_university.getAndIncrement(), "Mahidol University", "MU"));
        
        //Education
        //educations.add(new Education(String degree, String uName)); uName get method universities.get(index).getName()
        educations.add(new Education("Bachelor's Degree", universities.get(0).getName()));
     
        //Student
        //students.add(new Student(int id,String name, List<Education> education)); List<Education> education get educations
        students.add(new Student(counter_student.getAndIncrement(), "Parichaya Thanawuthikrai", educations));
        MapStudentUniversity();
    }
```
