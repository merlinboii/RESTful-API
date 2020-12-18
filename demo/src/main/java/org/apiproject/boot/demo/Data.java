package org.apiproject.boot.demo;

public class Data {
    private long id;
    private String content;
    
    public Data(long id,String content){
        this.id = id;
        this.content = content;
    }

    public long setId(){
        return id;
    }

    public String setContent(){
        return content;
    }

    public long getId(){
        return id;
    }

    public String getContent(){
        return content;
    }
}
