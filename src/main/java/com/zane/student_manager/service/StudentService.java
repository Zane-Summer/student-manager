package com.zane.student_manager.service;


import com.zane.student_manager.dto.StudentResponse;
import com.zane.student_manager.entity.Student;
import com.zane.student_manager.exception.StudentNotFoundException;
import com.zane.student_manager.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repository;

    private StudentResponse toResponse(Student s) {
        return new StudentResponse(s.getId(), s.getName(), s.getScore());
    }

    @Transactional
    public StudentResponse addStudent(String name, int score) {
        Student s = new Student();
        s.setName(name);
        s.setScore(score);
        return toResponse(repository.save(s));
    }

    public StudentResponse findById(Long id) {
        return toResponse(repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id)));
    }

    public Page<StudentResponse> getAllStudents(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    @Transactional
    public void removeStudentBelowScore(int threshold) {
        repository.deleteByScoreLessThan(threshold);
    }

    public List<StudentResponse> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void updateStudentScore(Long id, int newScore) {
        if (newScore < 0 || newScore > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }
        Student s = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        s.setScore(newScore);
    }

    public List<StudentResponse> findStudentsByMaxScore(int maxScore) {
        return repository.findByScoreLessThanEqual(maxScore)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public Page<StudentResponse> getTopNStudents(int n) {
        return repository.findAll(
                PageRequest.of(0, n, Sort.by(Sort.Direction.DESC, "score").and(Sort.by(Sort.Direction.ASC, "name")))
        ).map(this::toResponse);
    }

    public Long countStudentsInScoreRange(int min, int max) {
        return repository.countStudentsInScoreRange(min, max);
    }
}