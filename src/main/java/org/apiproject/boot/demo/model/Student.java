package  org.apiproject.boot.demo.model;

import java.util.List;

public class Student {
    private long id ;
    private String name;
    private List<Education> education;
    
    public Student(long id, String name, List<Education> education) {
            this.id = id;
            this.name = name;
            this.education = education;
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

    public List<Education> getEducation() {
        return education;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }
    
}
