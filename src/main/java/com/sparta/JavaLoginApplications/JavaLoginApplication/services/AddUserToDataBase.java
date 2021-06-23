package com.sparta.JavaLoginApplications.JavaLoginApplication.services;

import com.sparta.JavaLoginApplications.JavaLoginApplication.entities.User;
import com.sparta.JavaLoginApplications.JavaLoginApplication.entities.UsersEntity;

import javax.persistence.*;

public class AddUserToDataBase {
    public static void addUserToDataBase(User user){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();

            UsersEntity usersEntity = new UsersEntity();
            usersEntity.setName(user.getUsername());
            usersEntity.setPassword(user.getPassword());
            usersEntity.setAdmin(true);

            manager.persist(usersEntity);
            transaction.commit();
        } finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
            manager.close();
            factory.close();
        }
    }
}
