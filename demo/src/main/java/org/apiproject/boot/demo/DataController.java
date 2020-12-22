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
    private List<Education> educations_2 = new ArrayList<>(); 
    private List<UniversityInfo> universities = new ArrayList<>();
    private final AtomicLong counter_student = new AtomicLong();
    private final AtomicLong counter_university = new AtomicLong();

    public DataController() {

        // Sample Data
        universities.add(new UniversityInfo(counter_university.getAndIncrement(), "Mahidol University", "MU"));
        universities.add(new UniversityInfo(counter_university.getAndIncrement(), "Thammasat University", "TU"));
        universities.add(new UniversityInfo(counter_university.getAndIncrement(), "Chulalongkorn University", "CU"));

        educations.add(new Education("Bachelor's Degree", universities.get(0).getName()));
        educations.add(new Education("Master's Degree", universities.get(1).getName()));
        educations_2.add(new Education("Bachelor's Degree", universities.get(0).getName()));

        students.add(new Student(counter_student.getAndIncrement(), "Parichaya", educations));
        students.add(new Student(counter_student.getAndIncrement(), "Malakor", educations_2));
        MapStudentUniversity();
    }

    public void MapStudentUniversity() {
        int innerLoop, finalLoop;
        String uniName_Std, uniName_U;
        for (int i = 0; i < students.size(); i++) {
            innerLoop = students.get(i).getEducation().size();
            for (int j = 0; j < innerLoop; j++) {
                uniName_Std = students.get(i).getEducation().get(j).getuName();
                finalLoop = universities.size();
                for (int k = 0; k < finalLoop; k++) {
                    uniName_U = universities.get(k).getName();
                    if (uniName_Std.equalsIgnoreCase(uniName_U))
                        universities.get(k).addName_std(students.get(i).getName());
                }
            }
        }
    }

    public void checkUniversityName() {

    }

    ////////////////////// UNIVERSIRY //////////////////////
    @GetMapping("/universitiesall")
    public List<UniversityInfo> getAllUniversity() {
        return universities;
    }

    // .../universities
    // return all universities
    @GetMapping("/universities")
    public List<String> getUniversity() {
        return universities.stream().map(names -> names.getName()).collect(Collectors.toList());
    }

    // return University data as well as all of name student who study in
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/universities/{id}")
    public UniversityInfo getUniversitybyId(@PathVariable() long id) {
        return universities.stream().filter(result -> result.getId() == id).findFirst().orElseThrow(() -> new DataNotFoundException(id));

    }

    // Add new university
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/universities")
    public void addUniversity(@RequestBody UniversityInfo university) {
        try{
            int loop = universities.size();
            for (int k = 0; k < loop; k++) {
                String uniName_U = universities.get(k).getName();
                if (university.getName().equalsIgnoreCase(uniName_U))
                throw new DataCannotCreateException();
                else{
                    universities.add(new UniversityInfo(counter_university.getAndIncrement(), university.getName(),university.getNameInit()));
                }
            }
            
        }catch(DataCannotCreateException ex){
            throw new DataCannotCreateException();
         }
        
    }

    // Edit University info
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/universities/{id}")
    public void updateUniversity(@RequestBody UniversityInfo university, @PathVariable long id) {
        universities.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
            result.setName(university.getName());
            result.setName_init(university.getNameInit());
        }, () -> {
            throw new DataNotFoundException(id);
        });
    }

    // Delete university
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/universities/{id}")
    public void DeleteUniversity(@PathVariable long id) {
        universities.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
            universities.remove(result);
        }, () -> {
            throw new DataNotFoundException(id);
        });
    }

    ////////////////////// STUDENT //////////////////////
    @GetMapping("/studentsall")
    public List<Student> getAllStudents() {
        return students;
    }

    // return all students
    @GetMapping("/students")
    public List<String> getStudents() {
        return students.stream().map(names -> names.getName()).collect(Collectors.toList());
    }

    // return student data as well as all of name university that study in
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/students/{id}")
    public Student getStudentbyId(@PathVariable() long id) {
        return students.stream().filter(result -> result.getId() == id).findFirst().orElseThrow(() -> new DataNotFoundException(id));
    }

    // Add new student
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/students")
    public void addStudent(@RequestBody Student student) {
        try{
            students.add(new Student(counter_student.getAndIncrement(), student.getName(), student.getEducation()));
        }catch(DataCannotCreateException ex){
           throw new DataCannotCreateException();
        }
        
    }

    // Edit student info
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/students/{id}")
    public void updateStudent(@RequestBody Student student, @PathVariable long id) {
        students.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
            result.setName(student.getName());
            result.setEducation(student.getEducation());
        }, () -> {
            throw new DataNotFoundException(id);
        });
    }

    // Delete student
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
 * // .../data/search?name=...name...
 * 
 * @GetMapping("/data/search") public String
 * getDataByname(@RequestParam(defaultValue = "NaN") String name) { return
 * "search" + name; }
 */
