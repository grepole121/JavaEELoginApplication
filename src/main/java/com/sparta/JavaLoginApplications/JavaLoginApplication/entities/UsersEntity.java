package com.sparta.JavaLoginApplications.JavaLoginApplication.entities;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "users")
@NamedQueries({
        @NamedQuery(name = "users.getAll", query = "SELECT users FROM UsersEntity users"),
        @NamedQuery(name = "users.getById", query = "SELECT users FROM UsersEntity users WHERE users.id = :id"),
        @NamedQuery(name = "users.getByName", query = "SELECT users FROM UsersEntity users WHERE users.name = :name"),
        @NamedQuery(name = "users.updatePermission", query = "UPDATE UsersEntity users SET users.isAdmin = :true")
})
public class UsersEntity {
    private Integer id;
    private String name;
    private String password;
    private Boolean isAdmin;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "isAdmin")
    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(password, that.password) && Objects.equals(isAdmin, that.isAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, isAdmin);
    }

    private String isRegistered(String username) {
        return username.equals("Adrian") ? "You're registered" : "Registration required";
    }

    private HashMap<String, String> storeUsers(String filePath){
        HashMap<String, String> map = new HashMap<>();
        String lineRead;

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
            while((lineRead = bufferedReader.readLine()) != null){
                String[] loginCredentials = lineRead.split(",");
                map.put(loginCredentials[0].trim(), loginCredentials[1].trim());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    private String getPath(boolean valid){
        if(valid) return "WEB-INF/welcome.jsp";
        else {
            return "WEB-INF/wrong.jsp";
        }
    }

    private boolean validate(HashMap<String,String> storeUsers, String username, String password) {
        if(storeUsers.get(username) == null){
            return false;
        }
        else{
            return storeUsers.get(username).equals(password);
        }
    }
}
