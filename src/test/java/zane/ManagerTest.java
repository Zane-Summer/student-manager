package zane;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {
    @Test
    void addStudentTest() {
        Manager m = new Manager();
        m.addStudent("Alice", 90);
        assertEquals(1, m.getStudentListSize());
    }
    @Test
    void removeStudentTest() {
        Manager m = new Manager();
        m.addStudent("A", 50);
        m.addStudent("B", 70);
        int removed = m.removeStudentsBelowScore(60);
        assertEquals(1,removed);
        assertEquals(1,m.getStudentListSize());

        boolean hasB = m.getStudentList().stream().anyMatch(s -> s.getName().equals("B"));
        assertTrue(hasB);

        boolean allAbove = m.getStudentList().stream().allMatch(student -> student.getScore() >= 60);
        assertTrue(allAbove);
    }
}
