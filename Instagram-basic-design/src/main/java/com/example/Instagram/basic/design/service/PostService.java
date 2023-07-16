package com.example.Instagram.basic.design.service;

import com.example.Instagram.basic.design.model.Post;
import com.example.Instagram.basic.design.model.User;
import com.example.Instagram.basic.design.repositroy.IPostRepo;
import com.example.Instagram.basic.design.repositroy.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostService {

    @Autowired
    IPostRepo postRepo;

    @Autowired
    IUserRepo userRepo;

    @Autowired
    PostService postService;

    public String createInstaPost(Post post, String email) {
        User user = userRepo.findFirstByUserEmail(email);

        return postService.createInstaPost(post, String.valueOf(user));
    }

    public List<Post> getPostOfSimilarType(String userEmail) {
        User user = userRepo.findFirstByUserEmail(userEmail);
        Integer userId = user.getUserId();
        return postRepo.findByUserAndUserId(user,userId);
    }


}
