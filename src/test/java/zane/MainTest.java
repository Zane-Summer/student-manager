package zane;

public class MainTest {
    public static void main(String[] args) {
        // 1. create manager
        Manager manager = new Manager();

        // 2. work
        manager.addStudent("Alice", 85);
        manager.addStudent("Bob", 92);
        manager.addStudent("Charlie", 78);
        manager.addStudent("D", 48);
        manager.addStudent("E", 58);

        // 3. print all students
        manager.printAllStudents();

        // 4. print students below score 80
        manager.printStudentsBelowScore(80);

        // 5. remove students below score 100
        manager.removeStudentsBelowScore(100);

        // 6. print all students again
        manager.printAllStudents();
    }
}