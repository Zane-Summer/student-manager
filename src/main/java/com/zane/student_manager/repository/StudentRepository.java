package com.zane.student_manager.repository;

import com.zane.student_manager.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    void deleteByScoreLessThan(int threshold);

    List<Student> findByNameContainingIgnoreCase(String name);

    List<Student> findByScoreLessThanEqual(int maxScore);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.score BETWEEN :min AND :max")
    long countStudentsInScoreRange(@Param("min") int min, @Param("max") int max);
}