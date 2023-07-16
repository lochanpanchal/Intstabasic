package com.example.Instagram.basic.design.controller;

import com.example.Instagram.basic.design.model.User;
import com.example.Instagram.basic.design.model.dto.SignInInput;
import com.example.Instagram.basic.design.model.dto.SignUpOutput;
import com.example.Instagram.basic.design.service.AuthenticationService;
import com.example.Instagram.basic.design.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    //sign in
    //sing up
    //update user details


    @PostMapping("user/signup")
    public SignUpOutput signUpInstaUser(@RequestBody User user)
    {

        return userService.signUpUser(user);
    }

    @PostMapping("user/signIn")
    public String signInInstaUser(@RequestBody @Valid SignInInput signInInput)
    {
        return userService.signInUser(signInInput);
    }



    //updated user

    @PutMapping("user/{userId}")
    public ResponseEntity<User> updateUserDetails(@PathVariable Long userId, @RequestBody User updateUserRequest){
        User user = userService.findUserById(userId);

        //Check if the user exists
        if(user == null){
            return ResponseEntity.notFound().build();
        }

        //Update the user details with the provided data
        user.setUserFirstName(updateUserRequest.getUserFirstName());
        user.setUserLastName(updateUserRequest.getUserLastName());
        user.setUserAge(updateUserRequest.getUserAge());
        user.setUserPhoneNumber(updateUserRequest.getUserPhoneNumber());

        // email and password are not allow to update
        //save the updated user using the userService
        User updateUser = userService.saveUser(user);

        //return the updated user
        return ResponseEntity.ok(updateUser);


    }







}
