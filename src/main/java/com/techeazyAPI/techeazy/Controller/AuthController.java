package com.techeazyAPI.techeazy.Controller;


import com.techeazyAPI.techeazy.Models.User;
import com.techeazyAPI.techeazy.UserAuthentication.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthService authservice;

    @PostMapping("/register/{username}/{password}")
    public User register(@PathVariable String username, @PathVariable String password ) {
        return authservice.registerUser(username,password );

    }

    @PostMapping("/login/{username}/{password}")
    public String login(@PathVariable String username, @PathVariable String password ) {
        return authservice.loginUser(username,password );

    }


}
