package org.apiproject.boot.demo;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private long id ;
    private String name;
    private List<StudentU> university ;
    
    public Data(long id,String name,List<StudentU> university){
            this.id = id;
            this.name = name;
            this.university = university;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StudentU getUniversity() {
        return university;
    }

    public void setUniversity(StudentU university) {
        this.university = university;
    }

}
