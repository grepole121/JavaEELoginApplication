package com.sparta.JavaLoginApplications.JavaLoginApplication.beans;

import com.sparta.JavaLoginApplications.JavaLoginApplication.entities.User;
import com.sparta.JavaLoginApplications.JavaLoginApplication.services.UsersRepository;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class UserBean {
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


    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    //check if user is valid
    public String validUserCheck() {
        String name = getUser().getName();
        String password = getUser().getPassword();
        String output = "index";
        for (int i = 0; i < userRepo.getUsers().size(); i++) {
            if (name.equals(userRepo.getUsers().get(i).getName())
                    && (password.equals(userRepo.getUsers().get(i).getPassword()))) {
                getUser().setRole(userRepo.getUsers().get(i).getRole());
                //continueAuthentication();
                output = "view/welcome.xhtml";
                return output;
            }
        }
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "INVALID LOGIN", null));
        return output;
    }

    public void submit() throws IOException {

//        Attempt at debugging
//        Name and password are equal but login doesn't work
        FileWriter myWriter = new FileWriter("filename.txt", true);
        myWriter.write("\n" + getUsers().get(0).getId());
        myWriter.write("\n" + getUsers().get(0).getName() + "===" + name + " #" + name.equals(getUsers().get(0).getName()));
        myWriter.write("\n" + getUsers().get(0).getPassword() + "===" + password + " #" + password.equals(getUsers().get(0).getPassword()));
        myWriter.write("\n" + getUsers().get(0).getRole() + "===" + role);
        myWriter.write("\n" + getUsers().size());
        myWriter.close();

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
                AuthenticationParameters.withParams().credential(new UsernamePasswordCredential(name, password)));
    }

    public String logout() {
        ExternalContext externalContext = facesContext.getExternalContext();
        try {
            ((HttpServletRequest) externalContext.getRequest()).logout();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        facesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml?faces-redirect=true";
    }


    public String registration() throws IOException {
        userRepo.addUser(name, password, role);
        FileWriter myWriter = new FileWriter("C:\\Users\\Sparta\\Documents\\Java EE\\JavaEELoginApplication\\src\\main\\webapp\\resources\\users.csv", true);
        String user = "\n" + "1," + name + "," + password + "," + role;
        myWriter.write(user);
        myWriter.close();
        return "admin_page.xhtml?faces-redirect=true";
    }

    public static List<User> getUsers() {
        List<User> userList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Sparta\\Documents\\Java EE\\JavaEELoginApplication\\src\\main\\webapp\\resources\\users.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] user = line.split(",");
                User user1 = new User();
                user1.setId(Integer.parseInt(user[0].trim()));
                user1.setName(user[1].trim());
                user1.setPassword(user[2].trim());
                user1.setRole(user[3].trim());
                userList.add(user1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }
}


