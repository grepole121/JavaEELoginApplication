package com.sparta.JavaLoginApplications.JavaLoginApplication.authentication;

import com.sparta.JavaLoginApplications.JavaLoginApplication.beans.UserBean;
import com.sparta.JavaLoginApplications.JavaLoginApplication.entities.User;
import com.sparta.JavaLoginApplications.JavaLoginApplication.services.UsersRepository;

import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DataStore implements IdentityStore {

    @Inject
    UserBean userBean;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        ArrayList<User> userArrayList = (ArrayList<User>) userBean.getUsers();
        List<User> userList = userBean.getUsers();
        UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
        for (int i = 0; i< userList.size(); i++){
            if (usernamePasswordCredential.getCaller().equals(userList.get(i).getName())
                    && (usernamePasswordCredential.getPasswordAsString().equals(userList.get(i).getPassword()))) {
                if (userList.get(i).getRole() == "ADMIN"){
                    HashSet<String> roles = new HashSet<>();
                    roles.add("ADMIN");
                    return new CredentialValidationResult(userList.get(i).getName(), roles);
                }
                else if(userList.get(i).getRole() ==  "USER"){
                    HashSet<String> roles = new HashSet<>();
                    roles.add("USER");
                    return new CredentialValidationResult(userList.get(i).getName(), roles);
                }

            }
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;

    }
}