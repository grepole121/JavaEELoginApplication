package com.sparta.JavaLoginApplications.JavaLoginApplication.beans;


import com.sparta.JavaLoginApplications.JavaLoginApplication.entities.UsersEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScoped
public class LoginBean {
    @Inject
    UsersEntity usersEntity;

    public UsersEntity getUsersEntity() {
        return usersEntity;
    }

    public void setUsersEntity(UsersEntity usersEntity) {
        this.usersEntity = usersEntity;
    }

    public void login() throws IOException {

    }
}
