package org.apiproject.boot.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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
        students.add(new Student(counter_student.getAndIncrement(), "Parichaya Thanawuthikrai", educations));

        educations_2.add(new Education("Bachelor's Degree", universities.get(0).getName()));
        students.add(new Student(counter_student.getAndIncrement(), "Sathinee Thanawuthikrai", educations_2));

        MapStudentUniversity(0);
    }

    public void MapStudentUniversity(int c) {
        int innerLoop, finalLoop;
        String uniName_Std, uniName_U;
        int found = 0;
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
                            found = 1;
                        }

                    }
                }
                if (found != 1) {
                    students.remove(students.get(i));
                    counter_student.getAndDecrement();
                    throw new DataCannotCreateException(
                            "Could not created the data :: Can not found this university name in UniversityInfo.");
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
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping("/universities/{id}")
    public UniversityInfo getUniversitybyId(@PathVariable() long id) {
        return universities.stream().filter(result -> result.getId() == id).findFirst()
                .orElseThrow(() -> new DataNotFoundException(id));
    }

    // Add new university
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/universities")
    public void addUniversity(@RequestBody UniversityInfo university) throws INTERNAL_SERVER_ERROR {
        int loop = universities.size();
        try {
            for (int k = 0; k < loop; k++) {
                String uniName_U = universities.get(k).getName();
                if (university.getName().equalsIgnoreCase(uniName_U))
                    throw new DataCannotCreateException(
                            "Could not created the data :: Already has this university name");
            }
            universities.add(new UniversityInfo(counter_university.getAndIncrement(), university.getName(),
                    university.getName_init()));
        } catch (NullPointerException e) {
            counter_university.getAndDecrement();
            throw new INTERNAL_SERVER_ERROR();
        }

    }

    // Edit University info
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @PutMapping("/universities/{id}")
    public void updateUniversity(@RequestBody UniversityInfo university, @PathVariable long id)
            throws INTERNAL_SERVER_ERROR {
        int found = 0;
        String temp_name, temp_name_init;
        for (int i = 0; i < universities.size(); i++) {
            if (universities.get(i).getId() == id) {
                found = 1;
                temp_name = universities.get(i).getName();
                temp_name_init = universities.get(i).getName_init();
                try {
                    universities.get(i).setName(university.getName());
                    universities.get(i).setName_init(university.getName_init());
                    for (int j = 0; j < universities.size(); j++) {
                        if (j != i && universities.get(j).getName().equalsIgnoreCase(universities.get(i).getName())) {
                            universities.get(i).setName(temp_name);
                            universities.get(i).setName_init(temp_name_init);
                            throw new DataCannotCreateException(
                                    "Could not created the data :: Already has this university name");
                        }
                    }
                } catch (NullPointerException e) {
                    universities.get(i).setName(temp_name);
                    universities.get(i).setName_init(temp_name_init);
                    throw new INTERNAL_SERVER_ERROR();
                }

            }
        }
        if (found != 1) {
            throw new DataNotFoundException(id);
        }
    }

    // Delete university
    @ResponseStatus(HttpStatus.NOT_FOUND)
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
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @GetMapping("/students/{id}")
    public Student getStudentbyId(@PathVariable() long id) {
        return students.stream().filter(result -> result.getId() == id).findFirst()
                .orElseThrow(() -> new DataNotFoundException(id));

    }

    // Add new student
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @PostMapping("/students")
    public void addStudent(@RequestBody Student student) throws INTERNAL_SERVER_ERROR {

        try {
            students.add(new Student(counter_student.getAndIncrement(), student.getName(), student.getEducation()));
            MapStudentUniversity(1);
        } catch (NullPointerException e) {
            students.remove(students.get(students.size() - 1));
            counter_student.getAndDecrement();
            throw new INTERNAL_SERVER_ERROR();
        }
    }

    // Edit student info
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @PutMapping("/students/{id}")
    public void updateStudent(@RequestBody Student student, @PathVariable long id) throws INTERNAL_SERVER_ERROR {

        int found = 0;
        List<Education> temp_edu = new ArrayList<>();
        List<Integer> index = new ArrayList<>();
        List<Integer> index_backup = new ArrayList<>();
        // int remove=0;
        for (int i = 0; i < students.size(); i++) {
            Student target_std = students.get(i);

            for (int t = 0; t < target_std.getEducation().size(); t++) {
                temp_edu.add(new Education(target_std.getEducation().get(t).getDegree(),
                        target_std.getEducation().get(t).getuName()));
            }

            if (target_std.getId() == id) {
                found = 1;
                String old_std_name = target_std.getName();
                try {
                    for (int j = 0; j < universities.size(); j++) {
                        UniversityInfo target_uni = universities.get(j);
                        for (int k = 0; k < target_uni.getName_std().size(); k++) {
                            // std name in uni[j] == std name in Std[i]?
                            if (target_uni.getName_std().get(k).equalsIgnoreCase(old_std_name)) {
                                index_backup.add(j);
                                target_std.setName(student.getName());// new name
                                target_std.setEducation(student.getEducation());// new
                                // Already SET in Student class
                                target_uni.getName_std().remove(target_uni.getName_std().get(k));// remove when found
                                                                                                 // equals

                            }
                        }
                    }

                    for (int z = 0; z < universities.size(); z++) {
                        for (int x = 0; x < target_std.getEducation().size(); x++) {
                            if (target_std.getEducation().get(x).getuName()
                                    .equalsIgnoreCase(universities.get(z).getName())) {
                                index.add(z); // save uni index that is found.
                            }
                            if (index.size() == target_std.getEducation().size()) {
                                for (int z_in = 0; z_in < index.size(); z_in++) {
                                    universities.get(index.get(z_in)).addName_std(target_std.getName());
                                }

                            }
                        }

                    }
                    if (index.size() != target_std.getEducation().size()) {
                        for (int in_b = 0; in_b < index_backup.size(); in_b++) {
                            universities.get(index_backup.get(in_b)).addName_std(old_std_name);
                        }
                        target_std.setName(old_std_name);
                        target_std.setEducation(temp_edu);
                        throw new DataCannotCreateException(
                                "Could not created the data :: Can not found this university name in UniversityInfo.");
                    }

                } catch (NullPointerException e) {
                    for (int in_b = 0; in_b < index_backup.size(); in_b++) {
                        universities.get(index_backup.get(in_b)).addName_std(old_std_name);
                    }
                    target_std.setName(old_std_name);
                    target_std.setEducation(temp_edu);
                    throw new INTERNAL_SERVER_ERROR();
                }
            }
        }
        if (found != 1) {
            throw new DataNotFoundException(id);
        }
    }

    // Delete student
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @DeleteMapping("/students/{id}")
    public void DeleteStudent(@PathVariable long id) {
        students.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
            students.remove(result);
            counter_student.getAndDecrement();
        }, () -> {
            throw new DataNotFoundException(id);
        });
    }

}
