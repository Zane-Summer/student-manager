package zane;

import java.util.ArrayList;
import java.util.Iterator;

public class Manager {
    private final ArrayList<Student> studentList = new ArrayList<>();

    // add student
    public void addStudent(String name, int score){
        if (name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Student name cannot be empty");
        }
        if (score < 0 || score > 100){
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }
        studentList.add(new Student(name, score));
    }

    // remove student below a certain score
    public int removeStudentsBelowScore(int threshold){
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold cannot be negative");
        }
        int removedCount = 0;
        Iterator<Student> it = studentList.iterator();

        while (it.hasNext()){
            Student s = it.next();
            if (s.getScore() < threshold){
                it.remove();
                removedCount++;
            }
        }
        return removedCount;
    }

    // get studentList size
    public int getStudentListSize(){
        return studentList.size();
    }

    // get all students
    public ArrayList<Student> getAllStudents() {
        return new ArrayList<>(studentList); // defensive copy（防御性拷贝）
    }

    // get students below a certain score
    public ArrayList<Student> getStudentsBelowScore(int threshold) {
        ArrayList<Student> res = new ArrayList<>();
        for (Student s : studentList) {
            if (s.getScore() < threshold) res.add(s);
        }
        return res;
    }

    // update student score
    public boolean updateScoreByName(String name, int newScore){
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Student name cannot be blank");
        }
        for (Student s : studentList){
            if (s.getName().equals(name)){
                s.setScore(newScore);
                return true;
            }
        }
        return false;
    }

}
