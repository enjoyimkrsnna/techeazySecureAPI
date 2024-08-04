package com.techeazyAPI.techeazy.UserAuthentication;


import com.techeazyAPI.techeazy.Repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService{

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepo userRepo;

    private static final Logger log= LoggerFactory.getLogger(UserService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("inside loadUserByUsername");
        return userRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user not found"));

    }

}

