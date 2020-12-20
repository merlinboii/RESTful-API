package org.apiproject.boot.demo;

import java.util.ArrayList;
import java.util.List;
//import java.text.Format;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.stream.Stream;

import javax.naming.spi.DirStateFactory.Result;

import org.apiproject.boot.demo.Data.*;
import org.springframework.http.HttpStatus;
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

    private List<Data> datas = new ArrayList<>();
    private List<StudentU> students = new ArrayList<>();
    private List<UniversityInfo> universities = new ArrayList<>();
   // private final AtomicLong counter = new AtomicLong();

    public DataController() {
        UniversityInfo UniversityInfo_1 = new UniversityInfo(1, "Mahidol", "MU");
        UniversityInfo UniversityInfo_2 = new UniversityInfo(2, "Thamasat", "TU");
        universities.add(UniversityInfo_1);
        universities.add(UniversityInfo_2);
        StudentU StdInfo_1 = new StudentU("Bachelor",universities.get(0));
        StudentU StdInfo_2 = new StudentU("Master",universities.get(1));
        students.add(StdInfo_1);
        students.add(StdInfo_2);
        datas.add(new Data(1,"Parichaya",students));
        datas.add(new Data(2,"Chanakarn",students));
    }

    ////////////////////// UNIVERSIRY //////////////////////

    // .../universities 
    //return all universities
    @GetMapping("/universities")
    public List<String> getUniversities() {
        List<String> temp = new ArrayList<>();
        for(int i=0 ; i<universities.size(); i++){
            temp.add(universities.get(i).getName());
        }
        return temp;
    }
/*
    //return University data as well as all of name student who study in
    @GetMapping("/universities/{id}")
    public Data getDatasbyId(@PathVariable() long id) {
        return universities.stream().filter(result -> result.getId() == id).findFirst().orElseGet(() -> null);
    
    }
   
    //Add new university
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/universities")
    public void addData(@RequestBody Data datas) {
        datas.add(new Data(counter.getAndIncrement(), Data.getValue()));

    }

    //Edit University info
    @PutMapping("/universities/{id}")
    public void updateData(@RequestBody Data data, @PathVariable long id) {
        datas.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
            result.setValue(Data.getValue());
        }, () -> {
        });

    }

    //Delete university
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/universities/{id}")
    public void updateData(@PathVariable long id) {
        datas.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
            datas.remove(result);
        }, () -> {
            throw new DataNotFoundException(id)
        });

    }
*/
    ////////////////////// STUDENT //////////////////////
    //return all students
    @GetMapping("/students")
    public List<String> getStudents() {
        List<String> temp = new ArrayList<>();
        for(int i =0 ;i<datas.size();i++)
        {
            temp.add(datas.get(i).getName());
        }
        return temp;
     }

    //return student data as well as all of name student who study in
    @GetMapping("/students/{id}")
    public Data getDatabyId(@PathVariable() long id) {
        return datas.stream().filter(result -> result.getId() == id).findFirst().orElseGet(() -> null);
    
    }
/*   
    //Add new student
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/students")
    public void addData(@RequestBody Data data) {
        datas.add(new Data(counter.getAndIncrement(), Data.getValue()));

    }

    //Edit student info
    @PutMapping("/students/{id}")
    public void updateData(@RequestBody Data data, @PathVariable long id) {
        datas.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
            result.setValue(Data.getValue());
        }, () -> {
        });

    }

    //Delete student
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/students/{id}")
    public void updateData(@PathVariable long id) {
        datas.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
            datas.remove(result);
        }, () -> {
            throw new DataNotFoundException(id)
        });

    }



    // .../data/search?name=...name...
    @GetMapping("/data/search")
    public String getDataByname(@RequestParam(defaultValue = "NaN") String name) {
        return "search" + name;
    }
*/
}
