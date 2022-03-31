package com.example.tryout;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Employee {
    private SimpleStringProperty fnm,lnm,hireDate,dept,email,eid, ssn, phone;
    private SimpleDoubleProperty salary;
    public Employee(String eid, String fnm, String lnm, String ssn, String hiredate, String dept, Double sal, String email, String phone){
        this.eid = new SimpleStringProperty(eid);
        this.fnm = new SimpleStringProperty(fnm);
        this.lnm = new SimpleStringProperty(lnm);
        this.ssn = new SimpleStringProperty(ssn);
        this.hireDate = new SimpleStringProperty(hiredate);
        this.dept = new SimpleStringProperty(dept);
        this.salary = new SimpleDoubleProperty(sal);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
    }
    public String getEid(){return this.eid.get();}
    public String getFnm(){return this.fnm.get();}
    public String getLnm(){return this.lnm.get();}
    public String getSsn(){return this.ssn.get();}
    public String getHireDate(){return this.hireDate.get();}
    public String getDept(){return this.dept.get();}
    public double getSal(){return this.salary.get();}
    public String getEmail(){return this.email.get();}
    public String getPhone(){return this.phone.get();}
}
