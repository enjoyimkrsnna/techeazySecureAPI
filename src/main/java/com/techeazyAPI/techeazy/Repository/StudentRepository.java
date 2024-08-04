package com.techeazyAPI.techeazy.Repository;


import com.techeazyAPI.techeazy.Models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
