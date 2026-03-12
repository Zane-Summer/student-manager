package com.zane.student_manager.service;


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
    public Student addStudent(String name, int score) {
        Student s = new Student();
        s.setName(name);
        s.setScore(score);
        return repository.save(s);
    }

    public Student findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Page<Student> getAllStudents(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public void removeStudentBelowScore(int threshold) {
        repository.deleteByScoreLessThan(threshold);
    }

    public List<Student> findByNameContainingIgnoreCase(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    @Transactional
    public void updateStudentScore(Long id, int newScore) {
        Student s = repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        s.setScore(newScore);
    }

    public List<Student> findStudentsByMaxScore(int maxScore) {
        return repository.findByScoreLessThanEqual(maxScore);
    }

    public Page<Student> getTopNStudents(int n) {
        return  repository.findAll(PageRequest.of(0, n, Sort.by(Sort.Direction.DESC, "score")));
    }


}
