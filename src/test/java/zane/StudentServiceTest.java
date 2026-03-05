package zane;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {
    @Test
    void addStudentTest() {
        StudentService m = new StudentService();
        m.addStudent("Alice", 90);
        assertEquals(1, m.getStudentCount());
    }
    @Test
    void removeStudentTest() {
        StudentService m = new StudentService();
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
        StudentService m = new StudentService();
        assertThrows(IllegalArgumentException.class, () -> m.addStudent(null,90));
    }
    @Test
    void addStudent_shouldThrow_whenNameIsBlank(){
        StudentService m = new StudentService();
        assertThrows(IllegalArgumentException.class, () -> m.addStudent("   ",90));
    }
    @Test
    void addStudent_shouldThrow_whenScoreTooLow(){
        StudentService m = new StudentService();
        assertThrows(IllegalArgumentException.class, () -> m.addStudent("Alice",-1));
    }
    @Test
    void addStudent_shouldThrow_whenScoreTooHigh(){
        StudentService m = new StudentService();
        assertThrows(IllegalArgumentException.class, () -> m.addStudent("Alice",101));
    }
    @Test
    void updateScoreById_shouldUpdate_whenStudentExists(){
        StudentService m = new StudentService();
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
        StudentService m = new StudentService();
        boolean updated = m.updateScoreById("S999", 100);
        assertFalse(updated);
    }

    @Test
    void findById_shouldReturnStudent_whenExists(){
        StudentService m = new StudentService();
        Student s = m.addStudent("Alice", 90);
        assertTrue(m.findById(s.getId()).isPresent());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotFound(){
        StudentService m = new StudentService();
        assertTrue(m.findById("S999").isEmpty());
    }

    @Test
    void updateScoreByIdOrThrow_shouldUpdate_whenStudentExists() {
        StudentService m = new StudentService();
        Student alice = m.addStudent("Alice", 90);
        m.updateScoreByIdOrThrow(alice.getId(), 100);
        int score = m.findById(alice.getId())
                .orElseThrow()
                .getScore();
        assertEquals(100, score);
    }

    @Test
    void updateScoreByIdOrThrow_shouldThrow_whenStudentNotExists() {
        StudentService m = new StudentService();
        assertThrows(NoSuchElementException.class, () -> m.updateScoreByIdOrThrow("S999", 100));
    }
}
