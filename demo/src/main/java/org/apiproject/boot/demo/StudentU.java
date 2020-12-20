package org.apiproject.boot.demo;

public class StudentU {
    private String degree;
    private UniversityInfo uInfo ;

     public StudentU(String degree,UniversityInfo uInfo){
        this.degree=degree;
        this.uInfo=uInfo;
     }
     public void setDegree(String degree){
        this.degree=degree;
    }
    public String getDegree(){
        return degree;
    }

    public UniversityInfo getuInfo() {
        return uInfo;
    }

    public void setuInfo(UniversityInfo uInfo) {
        this.uInfo = uInfo;
    }  

}
