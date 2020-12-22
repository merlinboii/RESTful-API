package org.apiproject.boot.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
//import java.text.Format;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    private List<Student> students = new ArrayList<>(); 
    private List<Education> educations = new ArrayList<>();
    private List<Education> educations2 = new ArrayList<>(); // eduation / students
    //private List<List<Education>> edu_list = new ArrayList<>(); 
    private List<UniversityInfo> universities = new ArrayList<>();
    private List<String> student_list = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public DataController() {
        UniversityInfo UniversityInfo_1 = new UniversityInfo(1, "Mahidol University", "MU");
        UniversityInfo UniversityInfo_2 = new UniversityInfo(2, "Thammasat University", "TU");
        UniversityInfo UniversityInfo_3 = new UniversityInfo(3, "Chulalongkorn University", "CU");
        universities.add(UniversityInfo_1);
        universities.add(UniversityInfo_2);
        universities.add(UniversityInfo_3);

        Education StdEdu_1 = new Education("Bachelor's Degree",universities.get(0).getName());
        Education StdEdu_2 = new Education("Master's Degree",universities.get(1).getName());
        educations.add(StdEdu_1);
        educations.add(StdEdu_2);
        educations2.add(new Education("Bachelor's Degree",universities.get(0).getName()));

        students.add(new Student(1,"Parichaya",educations));
        students.add(new Student(2,"Malakor",educations2));

        for (int i = 0; i < students.size(); i++) {
            int innerLoop = students.get(i).getEducation().size();
            for (int j = 0; j < innerLoop; j++) {
                String uniName_std = students.get(i).getEducation().get(j).getuName();
                int finalLoop = universities.size();
                for (int k = 0; k < finalLoop; k++) {
                    String uniName_u = universities.get(k).getName();
                    if (uniName_std.equalsIgnoreCase(uniName_u))
                        universities.get(k).addName_std(students.get(i).getName());
                }
            }
        }
    }

    ////////////////////// UNIVERSIRY //////////////////////

    @GetMapping("/universitiesall")
    public List<UniversityInfo> getAllUniversity() {
        return universities;
    }
    // .../universities 
    //return all universities
    @GetMapping("/universities")
    public List<String> getUniversity() {
        return universities.stream().map(names -> names.getName()).collect(Collectors.toList());
        /*List<String> temp = new ArrayList<>();
        for(int i=0 ; i<universities.size(); i++){temp.add(universities.get(i).getName());
        }*/
    }
     //return University data as well as all of name student who study in
    @GetMapping("/universities/{id}")
    public UniversityInfo getUniversitybyId(@PathVariable() long id) {
        return universities.stream().filter(result -> result.getId() == id).findFirst().orElseGet(() -> null);
        
    }   
    //Add new university
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/universities")
    public void addUniversity(@RequestBody UniversityInfo university) {
        universities.add(new UniversityInfo(counter.getAndIncrement(), university.getName(),university.getNameInit()));
    }  
     //Edit University info
    @PutMapping("/universities/{id}")
    public void updateUniversity(@RequestBody UniversityInfo university, @PathVariable long id) {
        universities.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
            result.setName(university.getName());
            result.setName_init(university.getNameInit());
        }, () -> {});
    }
    //Delete university
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/universities/{id}")
    public void DeleteUniversity(@PathVariable long id) {
        universities.stream().filter(result -> result.getId() == id)
        .findFirst()
        .ifPresentOrElse(result -> {universities.remove(result);}, () -> {}); 
    }  


    ////////////////////// STUDENT //////////////////////
    @GetMapping("/studentsall")
    public List<Student> getAllStudents() {
        return students;
    }

    //return all students
    @GetMapping("/students")
    public List<String> getStudents() {
        return students.stream().map(names -> names.getName()).collect(Collectors.toList());
     }

    //return student data as well as all of name university that study in
    @GetMapping("/students/{id}")
    public Student getStudentbyId(@PathVariable() long id) {
        return students.stream().filter(result -> result.getId() == id).findFirst().orElseGet(() -> null);
    } 
     
     //Add new student
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/students")
    public void addStudent(@RequestBody Student student) {
       // educations.add(new Education(studentEdu.getDegree(),studentEdu.getUInfo()));
        students.add(new Student(counter.getAndIncrement(), student.getName(), student.getEducation()));
    }

    //Edit student info
    @PutMapping("/students/{id}")
    public void updateStudent(@RequestBody Student student, @PathVariable long id) {
        students.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
            result.setName(student.getName());
            result.setEducation(student.getEducation());
        }, () -> {
        });
    } 
    //Delete student
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/students/{id}")
    public void DeleteStudent(@PathVariable long id) {
        students.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
            students.remove(result);
        }, () -> {
            throw new DataNotFoundException(id);
        });
    } 

}

/*
    // .../data/search?name=...name...
    @GetMapping("/data/search")
    public String getDataByname(@RequestParam(defaultValue = "NaN") String name) {
        return "search" + name;
    }
*/
