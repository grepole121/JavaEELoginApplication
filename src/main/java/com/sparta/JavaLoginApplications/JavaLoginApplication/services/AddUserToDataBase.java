package com.sparta.JavaLoginApplications.JavaLoginApplication.services;

import com.sparta.JavaLoginApplications.JavaLoginApplication.entities.User;
import com.sparta.JavaLoginApplications.JavaLoginApplication.entities.UsersEntity;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUserToDataBase {
    @Resource (mappedName="jdbc/__default")
    DataSource ds;

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void addUserToDataBase(User user) throws SQLException {
//        AddUserToDataBase addUserToDataBase = new AddUserToDataBase();
//        Connection connection = addUserToDataBase.getConnection();

        PersistenceService persistenceService = new PersistenceService();
        EntityManager manager = persistenceService.getEntityManager();

//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
//        EntityManager manager = factory.createEntityManager();
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
//            factory.close();
        }


        //        String addUser = "INSERT INTO Users (username, password, isAdmin) VALUES(?, ?, ?);";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(addUser)) {
//            preparedStatement.setString(1, user.getUsername());
//            preparedStatement.setString(2, user.getPassword());
//            preparedStatement.setBoolean(3, user.getAdmin());
//
//            preparedStatement.executeUpdate();
//        }
    }
}
