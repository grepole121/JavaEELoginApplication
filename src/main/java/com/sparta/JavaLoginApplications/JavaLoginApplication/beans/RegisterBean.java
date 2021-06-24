package com.sparta.JavaLoginApplications.JavaLoginApplication.beans;


import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class RegisterBean {

    public String getRegisterForm(){

        return "register";
    }
}
