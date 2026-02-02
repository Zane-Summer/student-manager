package zane;

import org.junit.jupiter.api.Test;

public class MainTest {
    public static void main(String[] args) {
        // create manager
        Manager manager = new Manager();

        // work
        manager.addStudent("Alice", 85);
        manager.addStudent("Bob", 92);
        manager.addStudent("Charlie", 78);
        manager.addStudent("D", 48);
        manager.addStudent("E", 58);


    }
}