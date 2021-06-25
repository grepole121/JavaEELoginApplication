package com.sparta.JavaLoginApplications.JavaLoginApplication.services;

import com.sparta.JavaLoginApplications.JavaLoginApplication.entities.User;
import com.sparta.JavaLoginApplications.JavaLoginApplication.entities.UsersEntity;


import java.util.ArrayList;


public class UsersRepository {
    private ArrayList<UsersEntity> validUsers = new ArrayList<>();

    {
        addUser("Adrian", "password", "ADMIN");
        addUser("George", "password", "ADMIN");
        addUser("Jian", "password", "USER");
    }

    public ArrayList<UsersEntity> getUsers() {
        return validUsers;
    }

    public void addUser(String name, String password, String role) {
        UsersEntity user = new UsersEntity();
        int nextID = validUsers.size();
        user.setId(nextID);
        user.setName(name);
        user.setPassword(password);
        user.setRole(role);
        validUsers.add(user);
    }

}
