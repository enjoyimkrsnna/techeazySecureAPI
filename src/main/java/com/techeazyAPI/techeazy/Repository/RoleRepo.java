package com.techeazyAPI.techeazy.Repository;

import java.util.Optional;

import com.techeazyAPI.techeazy.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepo extends JpaRepository<Role, Integer>{

    Optional<Role> findByAuthority(String string);

}
