package zane;

import java.util.*;
import java.util.stream.Collectors;

public class Manager {
    private int idCounter = 1;
    private String generateId() {
        return String.format("S%03d", idCounter++);
    }

    private final Map<String,Student> studentMap = new HashMap<>();

    // add student
    public Student addStudent(String name, int score){
        String id = generateId();
        Student s = new Student(id, name, score);
        studentMap.put(id, s);
        return s;
    }

    // find student by id
    public Optional<Student> findById(String id){
        return Optional.ofNullable(studentMap.get(id));
    }

    // remove student below a certain score
    public int removeStudentsBelowScore(int threshold){
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold cannot be negative");
        }
        int beforeSize = studentMap.size();
        studentMap.values().removeIf(s -> s.getScore() < threshold);
        return beforeSize - studentMap.size();
    }

    // get student number
    public int getStudentCount(){
        return studentMap.size();
    }

    // get all students
    public List<Student> getAllStudents() {
        return new ArrayList<>(studentMap.values());
    }

    // get students below a certain score
    public List<Student> getStudentsBelowScore(int threshold) {
        return studentMap.values().stream()
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
        for (Student s : studentMap.values()){
            res.add(s.getName() + " - " + s.getScore());
        }
        return res;
    }

    // get all student views using stream
    public List<StudentView> getAllStudentViews(){
        return studentMap.values().stream()
                .map(s -> new StudentView(s.getName(), s.getScore()))
                .collect(Collectors.toList());
    }
}
