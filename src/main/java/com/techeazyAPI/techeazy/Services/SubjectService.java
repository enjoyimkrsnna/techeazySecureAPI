package com.techeazyAPI.techeazy.Services;



import com.techeazyAPI.techeazy.GlobalExceptionHandler.ResourceNotFoundException;
import com.techeazyAPI.techeazy.Models.Subject;
import com.techeazyAPI.techeazy.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject with ID " + id + " not found."));
    }

    public Subject getSubjectByName(String name) {
        return subjectRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Subject with name " + name + " not found."));
    }
}
