package com.zane.student_manager.service;


import com.zane.student_manager.dto.StudentResponse;
import com.zane.student_manager.entity.Student;
import com.zane.student_manager.exception.StudentNotFoundException;
import com.zane.student_manager.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {


    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentService service;

    @Test
    void findById_shouldReturnStudent_whenExists() {

        // given
        Student student = new Student(1L, "Zane", 99);
        when(repository.findById(1L)).thenReturn(Optional.of(student));

        // when
        StudentResponse result = service.findById(1L);

        // then
        assertEquals("Zane", result.getName());
        assertEquals(99, result.getScore());
    }

    @Test
    void findById_shouldReturnStudentNotFound_whenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void addStudent_shouldAddStudent_whenExists() {
        // given
        Student s = new Student(1L, "Zane", 99);
        when(repository.save(any(Student.class))).thenReturn(s);

        // when
        StudentResponse re =  service.addStudent("Zane", 99);

        // then
        assertEquals("Zane", re.getName());
        assertEquals(99, re.getScore());
    }

    @Test
    void removeStudentBelowScore_shouldRemoveStudentsBelowThreshold() {
        // when
        service.removeStudentBelowScore(60);

        // then
        verify(repository).deleteByScoreLessThan(60);
    }
}
