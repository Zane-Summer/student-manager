package com.zane.student_manager.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) {
        super("could not find student with id: " + id);
    }
}
