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

        boolean hasB = m.getAllStudents().stream().anyMatch(s -> s.getName().equals("B"));
        assertTrue(hasB);

        boolean allAbove = m.getAllStudents().stream().allMatch(student -> student.getScore() >= 60);
        assertTrue(allAbove);
    }
    @Test
    void addStudent_shouldThrow_whenNameIsNull(){
        Manager m = new Manager();
        assertThrows(IllegalArgumentException.class, () -> m.addStudent(null,90));
    }
    @Test
    void addStudent_shouldThrow_whenNameIsBlank(){
        Manager m = new Manager();
        assertThrows(IllegalArgumentException.class, () -> m.addStudent("   ",90));
    }
    @Test
    void addStudent_shouldThrow_whenScoreTooLow(){
        Manager m = new Manager();
        assertThrows(IllegalArgumentException.class, () -> m.addStudent("Alice",-1));
    }
    @Test
    void addStudent_shouldThrow_whenScoreTooHigh(){
        Manager m = new Manager();
        assertThrows(IllegalArgumentException.class, () -> m.addStudent("Alice",101));
    }
    @Test
    void updateScoreByName_shouldUpdate_whenStudentExists(){
        Manager m = new Manager();
        m.addStudent("Alice", 90);
        boolean updated = m.updateScoreByName("Alice", 100);
        assertTrue(updated);
        int score = m.getAllStudents().stream()
                .filter(s -> s.getName().equals("Alice"))
                .findFirst()
                .orElseThrow()
                .getScore();
        assertEquals(100, score);
    }
    @Test
    void updateScoreByName_shouldReturnFalse_whenStudentNotExists(){
        Manager m = new Manager();
        m.addStudent("Alice", 90);
        int oldSize = m.getStudentListSize();
        boolean updated = m.updateScoreByName("Bob", 80);
        assertFalse(updated);
        assertEquals(oldSize, m.getStudentListSize());
    }
}
