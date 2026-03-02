package zane;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {
    @Test
    void addStudentTest() {
        Manager m = new Manager();
        m.addStudent("Alice", 90);
        assertEquals(1, m.getStudentCount());
    }
    @Test
    void removeStudentTest() {
        Manager m = new Manager();
        m.addStudent("A", 50);
        m.addStudent("B", 70);
        int removed = m.removeStudentsBelowScore(60);
        assertEquals(1,removed);
        assertEquals(1,m.getStudentCount());

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
    void updateScoreById_shouldUpdate_whenStudentExists(){
        Manager m = new Manager();
        Student s = m.addStudent("Alice", 90);
        boolean updated = m.updateScoreById(s.getId(), 100);
        assertTrue(updated);
        int score = m.findById(s.getId())
                .orElseThrow()
                .getScore();
        assertEquals(100, score);
    }
    @Test
    void updateScoreById_shouldReturnFalse_whenStudentNotExists(){
        Manager m = new Manager();
        boolean updated = m.updateScoreById("S999", 100);
        assertFalse(updated);
    }

    @Test
    void findById_shouldReturnStudent_whenExists(){
        Manager m = new Manager();
        Student s = m.addStudent("Alice", 90);
        assertTrue(m.findById(s.getId()).isPresent());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotFound(){
        Manager m = new Manager();
        assertTrue(m.findById("S999").isEmpty());
    }

    @Test
    void updateScoreByIdOrThrow_shouldUpdate_whenStudentExists() {
        Manager m = new Manager();
        Student alice = m.addStudent("Alice", 90);
        m.updateScoreByIdOrThrow(alice.getId(), 100);
        int score = m.findById(alice.getId())
                .orElseThrow()
                .getScore();
        assertEquals(100, score);
    }

    @Test
    void updateScoreByIdOrThrow_shouldThrow_whenStudentNotExists() {
        Manager m = new Manager();
        assertThrows(NoSuchElementException.class, () -> m.updateScoreByIdOrThrow("S999", 100));
    }
}
