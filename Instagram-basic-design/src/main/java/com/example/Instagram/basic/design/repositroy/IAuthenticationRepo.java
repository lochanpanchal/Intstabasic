package com.example.Instagram.basic.design.repositroy;

import com.example.Instagram.basic.design.model.AuthenticationToken;
import com.example.Instagram.basic.design.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthenticationRepo extends JpaRepository<AuthenticationToken,Long> {


    AuthenticationToken findFirstByTokenValue(String authTokenValue);

    AuthenticationToken findFirstByUser(User user);
}
