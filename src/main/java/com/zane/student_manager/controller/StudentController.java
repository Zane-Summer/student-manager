package com.zane.student_manager.controller;


import com.zane.student_manager.entity.Student;
import com.zane.student_manager.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    @GetMapping
    public List<Student> getAll() {
        return service.getAllStudents();
    }

    @PostMapping
    public Student add(@RequestBody @Valid Student student) {
        return service.addStudent(student.getName(), student.getScore());
    }

    @DeleteMapping
    public void removeBelowScore(@RequestParam int threshold) {
        service.removeStudentBelowScore(threshold);
    }
}
