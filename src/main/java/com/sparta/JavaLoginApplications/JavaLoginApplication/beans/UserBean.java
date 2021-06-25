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
import java.io.IOException;

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

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
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
      facesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }
}


