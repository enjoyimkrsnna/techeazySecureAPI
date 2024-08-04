package com.techeazyAPI.techeazy;

import com.techeazyAPI.techeazy.Models.Role;
import com.techeazyAPI.techeazy.Models.User;
import com.techeazyAPI.techeazy.Repository.RoleRepo;
import com.techeazyAPI.techeazy.Repository.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class TecheazyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TecheazyApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepo roleRepository, UserRepo userRepository, PasswordEncoder passwordEncoder) {
		return args ->{
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			User admin = new User( "Admin", passwordEncoder.encode("password"), roles);

			userRepository.save(admin);
		};
	}

}
