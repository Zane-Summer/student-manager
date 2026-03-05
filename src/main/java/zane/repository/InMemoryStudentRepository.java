package zane.repository;

import zane.Student;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStudentRepository implements StudentRepository {
    private final Map<String, Student> studentMap = new ConcurrentHashMap<>();

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
