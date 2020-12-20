package org.apiproject.boot.demo;

public class UniversityInfo {
    private long id ;
    private String name;
    private String name_init;
    
    public UniversityInfo(long id,String name,String name_init){
        this.id = id;
        this.name = name;
        this.name_init = name_init;
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

    public String getNameInit(){
        return name_init;
    }
    public void setName_init(String name_init) {
        this.name_init = name_init;
    }
}
