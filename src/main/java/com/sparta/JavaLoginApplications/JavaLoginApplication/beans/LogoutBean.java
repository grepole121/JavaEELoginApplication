package com.sparta.JavaLoginApplications.JavaLoginApplication.beans;

import com.sparta.JavaLoginApplications.JavaLoginApplication.entities.UsersEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@RequestScoped
public class LogoutBean {
    @Inject
    UsersEntity usersEntity;

    public UsersEntity getUsersEntity() {
        return usersEntity;
    }

    public void setUsersEntity(UsersEntity userEntity) {
        this.usersEntity = usersEntity;
    }
}
