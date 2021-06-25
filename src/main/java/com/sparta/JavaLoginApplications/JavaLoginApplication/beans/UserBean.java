package com.sparta.JavaLoginApplications.JavaLoginApplication.beans;

import com.sparta.JavaLoginApplications.JavaLoginApplication.entities.User;
import com.sparta.JavaLoginApplications.JavaLoginApplication.services.UsersRepository;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;

@Named
@RequestScoped
public class UserBean {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private String password;
    private String role;

    @Inject
    User user;
    @Inject
    SecurityContext securityContext;

    @Inject
    ExternalContext externalContext;

    @Inject
    FacesContext facesContext;

    @Inject
    UsersRepository userRepo;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    //check if user is valid
    public String validUserCheck(){
        String name = getUser().getName();
        String password = getUser().getPassword();
        String output = "index";
        for (int i =0; i<userRepo.getUsers().size();i++){
            if (name.equals(userRepo.getUsers().get(i).getName())
                    && (password.equals(userRepo.getUsers().get(i).getPassword()))) {
                getUser().setRole(userRepo.getUsers().get(i).getRole());
                //continueAuthentication();
                output = "view/welcome.xhtml";
                return output;
            }
        }
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "INVALID LOGIN",null));
        return output;
    }

    public void submit() {
        switch (continueAuthentication()) {
            case SEND_CONTINUE:
                facesContext.responseComplete();
                break;
            case SEND_FAILURE:
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "INVALID LOGIN", null));
                break;
            case SUCCESS:
                try {
                    externalContext.redirect(externalContext.getRequestContextPath() + "/view/welcome.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    private AuthenticationStatus continueAuthentication() {
        return securityContext.authenticate((HttpServletRequest) externalContext.getRequest(),
                (HttpServletResponse) externalContext.getResponse(),
                AuthenticationParameters.withParams().credential(new UsernamePasswordCredential(user.getName(), user.getPassword())));
    }

    public String logout() {
        ExternalContext externalContext = facesContext.getExternalContext();
       try {
          ((HttpServletRequest)externalContext.getRequest()).logout();
      } catch (ServletException e) {
          e.printStackTrace();
       }
        facesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml?faces-redirect=true";
    }

    public String registration() throws IOException {
        userRepo.addUser(name,password,role);
        FileWriter myWriter = new FileWriter("filename.txt");
        myWriter.write(userRepo.getUsers().toString());
        myWriter.close();
        return "admin_page.xhtml?faces-redirect=true";
    }
}


