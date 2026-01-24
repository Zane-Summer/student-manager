package zane;

import java.util.ArrayList;
import java.util.Iterator;

public class Manager {
    private final ArrayList<Student> studentList = new ArrayList<>();

    // func 1: add student
    public void addStudent(String name, int score){
        studentList.add(new Student(name, score));
        System.out.println("Added student: " + name + " with score: " + score);
    }

    // func 2: print all students
    public void printAllStudents(){
        System.out.println("Student List:");
        for (Student s : studentList){
            System.out.println("Name: " + s.getName() + ", Score: " + s.getScore());
        }
    }

    // func 3: print all students below a certain score
    public void printStudentsBelowScore(int threshold){
        System.out.println("Students with scores below " + threshold + ":");
        for (Student s : studentList){
            if (s.getScore() < threshold){
                System.out.println("Name: " + s.getName() + ", Score: " + s.getScore());
            }
        }
    }

    // func 4: remove student below a certain score
    public int removeStudentsBelowScore(int threshold){
        System.out.println("Removing students with scores below " + threshold + ":");

        int removedCount = 0;
        Iterator<Student> it = studentList.iterator();

        while (it.hasNext()){
            Student s = it.next();
            if (s.getScore() < threshold){
                System.out.println("Removing student: " + s.getName() + " with score: " + s.getScore());
                it.remove();
                removedCount++;
            }
        }

        System.out.println("All students below score " + threshold + " have been removed.");
        return removedCount;
    }

    // func 5: get studentList size
    public int getStudentListSize(){
        return studentList.size();
    }

    // func 6: ArrayList<Student> getStudentList
    public ArrayList<Student> getStudentList(){
        return new ArrayList<>(studentList);
    }
}
