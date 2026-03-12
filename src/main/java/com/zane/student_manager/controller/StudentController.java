package com.zane.student_manager.controller;


import com.zane.student_manager.entity.Student;
import com.zane.student_manager.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService service;

    @GetMapping
    public Page<Student> getAll(Pageable pageable) {
        return service.getAllStudents(pageable);
    }

    @PostMapping
    public Student add(@RequestBody @Valid Student student) {
        return service.addStudent(student.getName(), student.getScore());
    }

    @DeleteMapping
    public void removeBelowScore(@RequestParam int threshold) {
        service.removeStudentBelowScore(threshold);
    }

    @GetMapping("/search")
    public List<Student> searchByName(@RequestParam String name) {
        return service.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("{id}")
    public Student getStudentById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}/score")
    public void updateStudentById(@PathVariable Long id, @RequestParam int newScore) {
        service.updateStudentScore(id, newScore);
    }

    @GetMapping("/score")
    public List<Student> getStudentsByMaxScore(@RequestParam int maxScore) {
        return service.findStudentsByMaxScore(maxScore);
    }

    @GetMapping("/top")
    public Page<Student> getTopNStudents(@RequestParam int n) {
        return service.getTopNStudents(n);
    }
}
