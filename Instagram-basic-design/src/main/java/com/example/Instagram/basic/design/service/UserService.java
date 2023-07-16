package com.example.Instagram.basic.design.service;

import com.example.Instagram.basic.design.model.AuthenticationToken;
import com.example.Instagram.basic.design.model.User;
import com.example.Instagram.basic.design.model.dto.SignInInput;
import com.example.Instagram.basic.design.model.dto.SignUpOutput;
import com.example.Instagram.basic.design.repositroy.IUserRepo;
import com.example.Instagram.basic.design.service.emailUtility.EmailHandler;
import com.example.Instagram.basic.design.service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    IUserRepo userRepo;

    @Autowired
    AuthenticationService authenticationService;

    public SignUpOutput signUpUser(User user) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getUserEmail();

        //check if input email is empty!!
        if(newEmail == null){
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //check if this user email already exists ??
        User existingUser = userRepo.findFirstByUserEmail(newEmail);

        if(existingUser != null)
        {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getUserPassword());

            //save encrypted password
            user.setUserPassword(encryptedPassword);
            userRepo.save(user);

            return new SignUpOutput(signUpStatus, "User registered successfully!!!");
        }
        catch (Exception e)
        {
            signUpStatusMessage = "Internal error occured during signup";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
    }

    public String signInUser(SignInInput signInInput) {

        String signInStausMessage = null;

        String signInEmail = signInInput.getEmail();


        //no email given as input
        if(signInEmail == null)
        {
            signInStausMessage = "Invalid email";
            return signInStausMessage;
        }

        //check if this user email already exists ??
        User existingUser = userRepo.findFirstByUserEmail(signInEmail);

        if(existingUser == null)
        {
            signInStausMessage = "Email not registered!!!";
            return signInStausMessage;
        }

        // match passwords
        //hash the password : encrpt the password

        try{
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());

            if(existingUser.getUserPassword().equals(encryptedPassword))
            {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken = new AuthenticationToken(existingUser);
                authenticationService.saveAuthToken(authToken);

                EmailHandler.sendEmail("lakerajpanchal98@gmail.com","email testing",authToken.getTokenValue());
                return "Token send to your email";
            }
            else{
                signInStausMessage = "Invalid credentials!!";
                return signInStausMessage;
            }

        }
        catch (Exception e){

            signInStausMessage = "Internal error occured during sign in";
            return signInStausMessage;

        }


    }


    public User findUserById(Long userId) {
        return  userRepo.findById(userId).orElse(null);
    }

    public User saveUser(User user) {
        return userRepo.save(user);
    }
}
