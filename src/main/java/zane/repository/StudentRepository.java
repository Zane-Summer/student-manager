package zane.repository;

import zane.Student;
import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    void save(Student s);
    Optional<Student> findById(String id);
    List<Student> findAll();
    void deleteById(String id);
}
