package com.techeazyAPI.techeazy.UserAuthentication;

import java.util.HashSet;
import java.util.Set;
import com.techeazyAPI.techeazy.Models.Role;
import com.techeazyAPI.techeazy.Models.User;
import com.techeazyAPI.techeazy.Repository.RoleRepo;
import com.techeazyAPI.techeazy.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authmanager;

    @Autowired
    private TokenService tokenService;

    public User registerUser(String username, String password) {
        if (userRepo.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        String encodedPassword = encoder.encode(password);
        Role userRole = roleRepo.findByAuthority("USER")
                .orElseThrow(() -> new RuntimeException("Role 'USER' does not exist"));

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        User user = new User(username, encodedPassword, authorities);
        return userRepo.save(user);
    }


    public String loginUser(String username, String password) {
        try {
            Authentication auth=authmanager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String token=tokenService.generateJWT(auth);
            return token;
        }catch (Exception e) {
            return "login failed ";
        }

    }

}
