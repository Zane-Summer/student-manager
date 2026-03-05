package zane;

import zane.repository.JsonFileStudentRepository;
import zane.repository.StudentRepository;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        StudentRepository repository = new JsonFileStudentRepository();
        StudentService service = new StudentService(repository);

        service.addStudent("Alice", 90);
        service.addStudent("Bob", 75);
        service.addStudent("Charlie", 60);

        List<Student> all = service.getAllStudents();
        System.out.println("All students:");
        for (Student s : all) {
            System.out.println(s.getId() + " - " + s.getName() + " - " + s.getScore());
        }

        int removed = service.removeStudentsBelowScore(70);
        System.out.println("Removed students below 70: " + removed);

        all = service.getAllStudents();
        System.out.println("After removal:");
        for (Student s : all) {
            System.out.println(s.getId() + " - " + s.getName() + " - " + s.getScore());
        }

        System.out.println("Check Students.json in project root folder.");
    }
}