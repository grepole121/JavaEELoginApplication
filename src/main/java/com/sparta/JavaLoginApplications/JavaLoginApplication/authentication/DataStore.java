package com.sparta.JavaLoginApplications.JavaLoginApplication.authentication;

import com.sparta.JavaLoginApplications.JavaLoginApplication.services.UsersRepository;

import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.HashSet;

public class DataStore implements IdentityStore {

    @Inject
    UsersRepository userRepo;
    @Override
    public CredentialValidationResult validate(Credential credential) {
        UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
        for (int i =0; i<userRepo.getUsers().size();i++){
            if (usernamePasswordCredential.getCaller().equals(userRepo.getUsers().get(i).getName())
                    && (usernamePasswordCredential.getPasswordAsString().equals(userRepo.getUsers().get(i).getPassword()))) {
                if (userRepo.getUsers().get(i).getRole() == "ADMIN"){
                    HashSet<String> roles = new HashSet<>();
                    roles.add("ADMIN");
                    return new CredentialValidationResult(userRepo.getUsers().get(i).getName(), roles);
                }
                else if(userRepo.getUsers().get(i).getRole() ==  "USER"){
                    HashSet<String> roles = new HashSet<>();
                    roles.add("USER");
                    return new CredentialValidationResult(userRepo.getUsers().get(i).getName(), roles);
                }

            }
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;

    }
}