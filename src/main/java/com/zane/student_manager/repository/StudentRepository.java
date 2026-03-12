package com.zane.student_manager.repository;

import com.zane.student_manager.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    void deleteByScoreLessThan(int threshold);

    List<Student> findByNameContainingIgnoreCase(String name);

    List<Student> findByScoreLessThanEqual(int maxScore);
}