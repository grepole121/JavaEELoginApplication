package com.sparta.JavaLoginApplications.JavaLoginApplication.services;

import com.sparta.JavaLoginApplications.JavaLoginApplication.entities.User;

import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;

@Named
@Stateless
public class LoginService {

    @Inject
    UsersRepository userRepo;

    @Inject
    User user1;
    @Inject
    SecurityContext securityContext;

    @Inject
    ExternalContext externalContext;

    @Inject
    FacesContext facesContext;


    public String registration(String username, String password,String role){
        userRepo.addUser(username,password,role);
        return "admin_page.xhtml?faces-redirect=true";
    }



}