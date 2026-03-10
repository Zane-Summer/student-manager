package com.zane.student_manager.service;


import com.zane.student_manager.entity.Student;
import com.zane.student_manager.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Student> findById(Long id) {
        return repository.findById(id);
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public void removeStudentBelowScore(int threshold) {
        List<Student> toRemove = repository.findAll().stream()
                .filter(s -> s.getScore() < threshold)
                .toList();
        repository.deleteAll(toRemove);
    }
}
