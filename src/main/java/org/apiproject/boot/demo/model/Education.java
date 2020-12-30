package org.apiproject.boot.demo.model;

public class Education {
    private String degree;
    private String uName;

    public Education(String degree, String uName) {
        this.degree = degree;
        this.setuName(uName);
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public void setDegree(String degree) {
        this.degree=degree;
    }
    public String getDegree(){
        return degree;
    }

}
