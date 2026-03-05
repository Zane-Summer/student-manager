package zane;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import zane.repository.StudentRepository;

@Slf4j
public class StudentService {
    private final StudentRepository repository;
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    private String generateId() {
        return String.format("S%03d", idCounter.getAndIncrement());
    }


    // add student
    public Student addStudent(String name, int score){
        log.info("Adding student with name {} and score {}", name, score);

        String id = generateId();
        Student s = new Student(id, name, score);
        repository.save(s);
        return s;
    }

    // find student by id
    public Optional<Student> findById(String id){
        return repository.findById(id);
    }

    // remove student below a certain score
    public int removeStudentsBelowScore(int threshold){
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold cannot be negative");
        }
        List<Student> all = repository.findAll();
        List<Student> toRemove = all.stream()
                .filter(s -> s.getScore() < threshold)
                .toList();
        toRemove.forEach(s -> repository.deleteById(s.getId()));
        return toRemove.size();
    }

    // get student number
    public int getStudentCount(){
        List<Student> all = repository.findAll();
        return all.size();
    }

    // get all students
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    // get students below a certain score
    public List<Student> getStudentsBelowScore(int threshold) {
        List<Student> all = repository.findAll();
        return all.stream()
                .filter(s -> s.getScore() < threshold)
                .toList();
    }

    // update student score
    public boolean updateScoreById(String id, int newScore){
        return findById(id)
                .map(s -> { s.setScore(newScore); return true;})
                .orElse(false);
    }

    // update student score by id or throw if not found
    public void updateScoreByIdOrThrow(String id,int newScore){
        Student s = findById(id).orElseThrow(() -> new NoSuchElementException("Student with id " + id + " not found"));
        s.setScore(newScore);
    }

    // get student view list
    public List<String> getStudentViewList(){
        List<String> res = new ArrayList<>();
        List<Student> all = repository.findAll();
        for (Student s : all){
            res.add(s.getName() + " - " + s.getScore());
        }
        return res;
    }

    // get all student views using stream
    public List<StudentView> getAllStudentViews(){
        List<Student> all = repository.findAll();
        return all.stream()
                .map(s -> new StudentView(s.getName(), s.getScore()))
                .collect(Collectors.toList());
    }
}

