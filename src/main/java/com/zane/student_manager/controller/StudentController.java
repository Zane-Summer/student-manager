package com.zane.student_manager.controller;


import com.zane.student_manager.dto.StudentRequest;
import com.zane.student_manager.dto.StudentResponse;
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
    public Page<StudentResponse> getAll(Pageable pageable) {
        return service.getAllStudents(pageable);
    }

    @PostMapping
    public StudentResponse add(@RequestBody @Valid StudentRequest request) {
        return service.addStudent(request.getName(), request.getScore());
    }

    @DeleteMapping
    public void removeBelowScore(@RequestParam int threshold) {
        service.removeStudentBelowScore(threshold);
    }

    @GetMapping("/search")
    public List<StudentResponse> searchByName(@RequestParam String name) {
        return service.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("{id}")
    public StudentResponse getStudentById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}/score")
    public void updateStudentById(@PathVariable Long id, @RequestParam int newScore) {
        service.updateStudentScore(id, newScore);
    }

    @GetMapping("/score")
    public List<StudentResponse> getStudentsByMaxScore(@RequestParam int maxScore) {
        return service.findStudentsByMaxScore(maxScore);
    }

    @GetMapping("/top")
    public Page<StudentResponse> getTopNStudents(@RequestParam int n) {
        return service.getTopNStudents(n);
    }
}
