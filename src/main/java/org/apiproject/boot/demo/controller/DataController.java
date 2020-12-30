package org.apiproject.boot.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

//import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import org.apiproject.boot.demo.model.*;
import org.apiproject.boot.demo.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        counter_university.getAndIncrement();
        counter_student.getAndIncrement();
        // Sample Data
        universities.add(new UniversityInfo(counter_university.getAndIncrement(), "Mahidol University", "MU"));
        universities.add(new UniversityInfo(counter_university.getAndIncrement(), "Thammasat University", "TU"));
        universities.add(new UniversityInfo(counter_university.getAndIncrement(), "Chulalongkorn University", "CU"));

        educations.add(new Education("Bachelor's Degree", universities.get(0).getName()));
        educations.add(new Education("Master's Degree", universities.get(1).getName()));
        educations_2.add(new Education("Bachelor's Degree", universities.get(0).getName()));

        students.add(new Student(counter_student.getAndIncrement(), "Parichaya Thanawuthikrai", educations));
        students.add(new Student(counter_student.getAndIncrement(), "Sathinee Thanaeuthikrai", educations_2));
        MapStudentUniversity(0);
    }

    public void MapStudentUniversity(int c) {
        int innerLoop, finalLoop;
        String uniName_Std, uniName_U;
        int check = 0;
        switch (c) {
            case 0:
                for (int i = 0; i < students.size(); i++) {
                    innerLoop = students.get(i).getEducation().size();
                    for (int j = 0; j < innerLoop; j++) {
                        uniName_Std = students.get(i).getEducation().get(j).getuName();
                        finalLoop = universities.size();
                        for (int k = 0; k < finalLoop; k++) {
                            uniName_U = universities.get(k).getName();
                            if (uniName_Std.equalsIgnoreCase(uniName_U)) {
                                universities.get(k).addName_std(students.get(i).getName());
                            }
                        }
                    }
                }
                break;
            case 1: {
                int i = students.size() - 1; // last student
                innerLoop = students.get(i).getEducation().size();
                for (int j = 0; j < innerLoop; j++) {
                    uniName_Std = students.get(i).getEducation().get(j).getuName();
                    finalLoop = universities.size();
                    for (int k = 0; k < finalLoop; k++) {
                        uniName_U = universities.get(k).getName();
                        if (uniName_Std.equalsIgnoreCase(uniName_U)) {
                            universities.get(k).addName_std(students.get(i).getName());
                            check = 1;
                        }
                    }
                }
                if (check == 0) {
                    students.remove(students.get(i));
                    throw new DataCannotCreateException(
                            "Could not created the data :: Can not found university name in UniversityInfo.");
                }
            }

                break;
            default:

                break;
        }

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
    @GetMapping("/universities/{id}")
    public UniversityInfo getUniversitybyId(@PathVariable() long id) {
        return universities.stream().filter(result -> result.getId() == id).findFirst().orElseThrow(()->new DataNotFoundException(id));
    }

    // Add new university
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/universities")
    public void addUniversity(@RequestBody UniversityInfo university) {
        try {
            int loop = universities.size();
            for (int k = 0; k < loop; k++) {
                String uniName_U = universities.get(k).getName();
                if (university.getName().equalsIgnoreCase(uniName_U))
                    throw new DataCannotCreateException(
                            "Could not created the data :: Already has this university name");
            }
            universities.add(new UniversityInfo(counter_university.getAndIncrement(), university.getName(),
                    university.getName_init()));

        } catch (DataCannotCreateException ex) {
            throw new DataCannotCreateException("Could not created the data");
        }

    }

    // Edit University info
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/universities/{id}")
    public void updateUniversity(@RequestBody UniversityInfo university, @PathVariable long id) {
        universities.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
            result.setName(university.getName());
            result.setName_init(university.getName_init());
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
        int index = (int) id - 1;
        while (index < universities.size()) {
            long temp = universities.get(index).getId();
            universities.get(index).setId(temp - 1);
            index++;
        }
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
    @GetMapping("/students/{id}")
    public Student getStudentbyId(@PathVariable() long id,@RequestBody Student student) {
        return students.stream().filter(result -> result.getId() == id).findFirst().orElseThrow(()->new DataNotFoundException(id));
    
    }

    // Add new student
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/students")
    public void addStudent(@RequestBody Student student) {
        try {
            students.add(new Student(counter_student.getAndIncrement(), student.getName(), student.getEducation()));
            MapStudentUniversity(1);
        } catch (DataCannotCreateException ex) {
            throw new DataCannotCreateException("Could not created the data");
        }

    }

    // Edit student info
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/students/{id}")
    public void updateStudent(@RequestBody Student student, @PathVariable long id) {

        int check = 0;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) { 
                for (int j = 0; j < universities.size(); j++) {
                    for (int k = 0; k < universities.get(j).getName_std().size(); k++) {
                        if (universities.get(j).getName_std().get(k).equalsIgnoreCase(students.get(i).getName())) {
                            students.get(i).setName(student.getName());
                            students.get(i).setEducation(student.getEducation()); 
                            universities.get(j).getName_std().remove(universities.get(j).getName_std().get(k));
                            for(int x=0;x<students.get(i).getEducation().size();x++){
                                if(students.get(i).getEducation().get(x).getuName().equalsIgnoreCase(universities.get(j).getName())){
                                    universities.get(j).addName_std(students.get(i).getName());
                                }
                               else{
                                    for (int y = 0; y < universities.size(); y++){
                                        if(students.get(i).getEducation().get(x).getuName().equalsIgnoreCase(universities.get(y).getName())){
                                            universities.get(y).addName_std(students.get(i).getName());
                                            check = 1;
                                        }
                                    }
                                }    
                            }      
                                

                        }
                    }
                    
                }
                
                if (check == 0) {
                    students.remove(students.get(i));
                    throw new DataCannotCreateException(
                            "Could not created the data :: Can not found university name in UniversityInfo.");
                }

            }
            else throw new DataNotFoundException(id); 
        }
    }

    // Delete student
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/students/{id}")
    public void DeleteStudent(@PathVariable long id) {
        students.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
            students.remove(result);
            counter_student.getAndDecrement();
        }, () -> {
            throw new DataNotFoundException(id);
        });
        int index = (int) id - 1;
        while (index < students.size()) {
            long temp = students.get(index).getId();
            students.get(index).setId(temp - 1);
            index++;
        }
    }

}
