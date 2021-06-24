package com.sparta.JavaLoginApplications.JavaLoginApplication.beans;

import com.sparta.JavaLoginApplications.JavaLoginApplication.entities.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@RequestScoped
public class UserBean {

    @Inject
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String addUser(){

        return "admin";
    }

}
