package com.techeazyAPI.techeazy.Repository;



import com.techeazyAPI.techeazy.Models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByName(String name);
}
