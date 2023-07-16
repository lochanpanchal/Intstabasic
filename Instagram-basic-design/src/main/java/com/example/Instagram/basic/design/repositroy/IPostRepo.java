package com.example.Instagram.basic.design.repositroy;

import com.example.Instagram.basic.design.model.Post;

import com.example.Instagram.basic.design.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepo extends JpaRepository<Post,Long> {


    List<Post> findByUserAndUserId(User user, Integer userId);
}
