package  org.apiproject.boot.demo.model;

import java.util.ArrayList;
import java.util.List;

public class UniversityInfo {
    private long id;
    private String name;
    private String name_init;
    private List<String> name_std = new ArrayList<>();

    public UniversityInfo(long id, String name, String name_init) {
        this.id = id;
        this.name = name;
        this.name_init = name_init;
        //this.name_std = name_std;
    }

    public void setId(long id){
        this.id=id;
    }
    public long getId(){
        return id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public String getName_init(){
        return name_init;
    }
    public void setName_init(String name_init) {
        this.name_init = name_init;
    }

    public List<String> getName_std() {
        return name_std;
    }

    public void setName_std(List<String> name_std) {
        this.name_std = name_std;
    }

    public void addName_std(String name){
        name_std.add(name);
    }

}
