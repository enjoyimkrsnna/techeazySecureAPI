package com.techeazyAPI.techeazy.Services;



import com.techeazyAPI.techeazy.DTO.StudentDTO;
import com.techeazyAPI.techeazy.GlobalExceptionHandler.ResourceNotFoundException;
import com.techeazyAPI.techeazy.Models.Student;
import com.techeazyAPI.techeazy.Models.Subject;
import com.techeazyAPI.techeazy.Repository.StudentRepository;
import com.techeazyAPI.techeazy.Repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public StudentService(StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    public Student createStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setAddress(studentDTO.getAddress());
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student enrollStudentInSubject(Long studentId, String subjectName) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Subject> subjectOpt = subjectRepository.findByName(subjectName);

        if (studentOpt.isEmpty()) {
            throw new ResourceNotFoundException("Student with ID " + studentId + " not found.");
        }

        if (subjectOpt.isEmpty()) {
            throw new ResourceNotFoundException("Subject with name '" + subjectName + "' not found.");
        }

        Student student = studentOpt.get();
        Subject subject = subjectOpt.get();

        if (student.getSubjects().contains(subject)) {
            throw new IllegalArgumentException("Student is already enrolled in the subject.");
        }

        student.getSubjects().add(subject);
        return studentRepository.save(student);
    }

}
