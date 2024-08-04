package com.techeazyAPI.techeazy.Controller;

import com.techeazyAPI.techeazy.DTO.StudentDTO;
import com.techeazyAPI.techeazy.GlobalExceptionHandler.CustomErrorResponse;
import com.techeazyAPI.techeazy.GlobalExceptionHandler.ResourceNotFoundException;
import com.techeazyAPI.techeazy.Models.Student;
import com.techeazyAPI.techeazy.Services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody StudentDTO studentDTO) {
        try {
            Student student = studentService.createStudent(studentDTO);
            return new ResponseEntity<>(convertToDTO(student), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    "Bad Request",
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST.value()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    "Internal Server Error",
                    "An unexpected error occurred: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            List<StudentDTO> studentDTOs = students.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(studentDTOs);
        } catch (Exception e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    "Internal Server Error",
                    "An unexpected error occurred: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @PostMapping("/enroll")
    public ResponseEntity<?> enrollStudentInSubject(@RequestParam Long studentId, @RequestParam String subjectName) {
        try {
            Student student = studentService.enrollStudentInSubject(studentId, subjectName);
            return ResponseEntity.ok(convertToDTO(student));
        } catch (IllegalArgumentException e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    "Bad Request",
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST.value()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (ResourceNotFoundException e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    "Not Found",
                    e.getMessage(),
                    HttpStatus.NOT_FOUND.value()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    "Internal Server Error",
                    "An unexpected error occurred: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }



    private StudentDTO convertToDTO(Student student) {
        if (student == null) {
            throw new ResourceNotFoundException("Student not found");
        }
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setAddress(student.getAddress());
        studentDTO.setSubjects(student.getSubjects().stream()
                .map(subject -> subject.getName())
                .collect(Collectors.toSet()));
        return studentDTO;
    }
}
