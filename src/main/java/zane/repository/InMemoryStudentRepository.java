package zane.repository;

import zane.Student;
import java.util.*;

public class InMemoryStudentRepository implements StudentRepository {
    private Map<String, Student> studentMap = new HashMap<>();

    @Override
    public void save(Student s){
        studentMap.put(s.getId(), s);
    }

    @Override
    public Optional<Student> findById(String id){
        return Optional.ofNullable(studentMap.get(id));
    }

    @Override
    public List<Student> findAll(){
        return new ArrayList<>(studentMap.values());
    }

    @Override
    public void deleteById(String id){
        studentMap.remove(id);
    }
}
