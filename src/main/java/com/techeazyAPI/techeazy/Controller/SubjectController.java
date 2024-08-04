package com.techeazyAPI.techeazy.Controller;

import com.techeazyAPI.techeazy.DTO.SubjectDTO;
import com.techeazyAPI.techeazy.GlobalExceptionHandler.CustomErrorResponse;
import com.techeazyAPI.techeazy.GlobalExceptionHandler.ResourceNotFoundException;
import com.techeazyAPI.techeazy.Models.Subject;
import com.techeazyAPI.techeazy.Services.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<?> createSubject(@RequestBody SubjectDTO subjectDTO) {
        try {
            Subject subject = new Subject();
            subject.setName(subjectDTO.getName());
            Subject createdSubject = subjectService.createSubject(subject);
            return new ResponseEntity<>(convertToDTO(createdSubject), HttpStatus.CREATED);
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
    public ResponseEntity<?> getAllSubjects() {
        try {
            List<Subject> subjects = subjectService.getAllSubjects();
            List<SubjectDTO> subjectDTOs = subjects.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(subjectDTOs);
        } catch (Exception e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(
                    "Internal Server Error",
                    "An unexpected error occurred: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    private SubjectDTO convertToDTO(Subject subject) {
        if (subject == null) {
            throw new ResourceNotFoundException("Subject not found");
        }
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(subject.getId());
        subjectDTO.setName(subject.getName());
        return subjectDTO;
    }
}

