package com.example.Instagram.basic.design.controller;

import com.example.Instagram.basic.design.model.Post;
import com.example.Instagram.basic.design.service.AuthenticationService;
import com.example.Instagram.basic.design.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    AuthenticationService authenticationService;


    //save post
    // get post

    @PostMapping("post")
    public String createInstaPost(@RequestBody Post post, @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return postService.createInstaPost(post,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @GetMapping("post/type")
    public List<Post> getPostOfSimilarType(@RequestParam String userEmail, @RequestParam String userToken)
    {
        boolean checkSignInUser = authenticationService.authenticate(userEmail,userToken);
        if(checkSignInUser)
        {
            return postService.getPostOfSimilarType(userEmail);
        }
        throw new IllegalStateException("Not a Authenticated user");
    }







}
