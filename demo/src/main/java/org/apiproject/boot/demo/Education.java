package org.apiproject.boot.demo;

public class Education {
    private String degree;
    private UniversityInfo uInfo ;

     public Education(String degree, UniversityInfo uInfo) {
        this.degree=degree;
        this.uInfo=uInfo;
     }
     public void setDegree(String degree){
        this.degree=degree;
    }
    public String getDegree(){
        return degree;
    }

    public UniversityInfo getUInfo() {
        return uInfo;
    }

    public void setUInfo(UniversityInfo uInfo) {
        this.uInfo = uInfo;
    }  

}
