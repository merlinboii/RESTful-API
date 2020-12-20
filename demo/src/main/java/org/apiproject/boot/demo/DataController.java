package org.apiproject.boot.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.text.Format;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

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
   // private final AtomicLong counter = new AtomicLong();

    public DataController() {
       StudentU myStdInfo_1 = new StudentU("Bachelor",new UniversityInfo(1,"Mahidol","MU"));
        StudentU myStdInfo_2 = new StudentU("Master",new UniversityInfo(1,"Mahidol","MU"));
        students.add(myStdInfo_1);
        students.add(myStdInfo_2);
        datas.add(new Data(1,"Parichaya",students));
        //datas.add(new Data(2,"Chanakarn",new StudentU("Bachalor",new UniversityInfo(1,"Mahidol","MU"))))
    }

    ////////////////////// UNIVERSIRY //////////////////////

    // .../universities 
    //return all universities
    @GetMapping("/universities")
    public List<Data> getDatas() {
        return datas;
    }
/*
    //return University data as well as all of name student who study in
    @GetMapping("/universities/{id}")
    public Data getDatasbyId(@PathVariable() long id) {
        return datas.stream().filter(result -> result.getId() == id).findFirst().orElseGet(() -> null);
    
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

    ////////////////////// STUDENT //////////////////////
    //return all students
    @GetMapping("/students")
    public List<Data> getData() {
        return datas;
    }
/*
    //return student data as well as all of name student who study in
    @GetMapping("/students/{id}")
    public Data getDatabyId(@PathVariable() long id) {
        return datas.stream().filter(result -> result.getId() == id).findFirst().orElseGet(() -> null);
    
    }
    
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
