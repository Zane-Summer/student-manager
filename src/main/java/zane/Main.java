package zane;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        manager.addStudent("Alice", 85);
        manager.addStudent("Bob", 92);
        manager.addStudent("Charlie", 78);
        manager.addStudent("D", 48);
        manager.addStudent("E", 58);

        System.out.println("All students:");
        for (Student s : manager.getAllStudents()){
            System.out.println(s.getName() + " - " + s.getScore());
        }

        int threshold = 60;

        System.out.println("\nStudents below score " + threshold + ":");
        for (Student s : manager.getStudentsBelowScore(threshold)){
            System.out.println(s.getName() + " - " + s.getScore());
        }

        int removedCount = manager.removeStudentsBelowScore(threshold);
        System.out.println("\nRemoved count (< " + threshold + "): " + removedCount);

        System.out.println("\nAfter removal:");
        for (Student s : manager.getAllStudents()){
            System.out.println(s.getName() + " - " + s.getScore());
        }
    }
}