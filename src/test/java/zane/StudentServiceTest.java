package zane;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zane.repository.InMemoryStudentRepository;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {
    private StudentService service;
    @BeforeEach
    void setUp() {
        service = new StudentService(new InMemoryStudentRepository());
    }
    
    @Test
    void addStudentTest() {
        service.addStudent("Alice", 90);
        assertEquals(1, service.getStudentCount());
    }

    @Test
    void removeStudentTest() {
        service.addStudent("A", 50);
        service.addStudent("B", 70);
        int removed = service.removeStudentsBelowScore(60);
        assertEquals(1,removed);
        assertEquals(1,service.getStudentCount());
        boolean hasB = service.getAllStudents().stream().anyMatch(s -> s.getName().equals("B"));
        assertTrue(hasB);
        boolean allAbove = service.getAllStudents().stream().allMatch(student -> student.getScore() >= 60);
        assertTrue(allAbove);
    }

    @Test
    void addStudent_shouldThrow_whenNameIsNull(){
        assertThrows(IllegalArgumentException.class, () -> service.addStudent(null,90));
    }

    @Test
    void addStudent_shouldThrow_whenNameIsBlank(){
        assertThrows(IllegalArgumentException.class, () -> service.addStudent("   ",90));
    }

    @Test
    void addStudent_shouldThrow_whenScoreTooLow(){
        assertThrows(IllegalArgumentException.class, () -> service.addStudent("Alice",-1));
    }

    @Test
    void addStudent_shouldThrow_whenScoreTooHigh(){
        assertThrows(IllegalArgumentException.class, () -> service.addStudent("Alice",101));
    }

    @Test
    void updateScoreById_shouldUpdate_whenStudentExists(){
        Student s = service.addStudent("Alice", 90);
        boolean updated = service.updateScoreById(s.getId(), 100);
        assertTrue(updated);
        int score = service.findById(s.getId())
                .orElseThrow()
                .getScore();
        assertEquals(100, score);
    }

    @Test
    void updateScoreById_shouldReturnFalse_whenStudentNotExists(){
        boolean updated = service.updateScoreById("S999", 100);
        assertFalse(updated);
    }

    @Test
    void findById_shouldReturnStudent_whenExists(){
        Student s = service.addStudent("Alice", 90);
        assertTrue(service.findById(s.getId()).isPresent());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotFound(){
        assertTrue(service.findById("S999").isEmpty());
    }

    @Test
    void updateScoreByIdOrThrow_shouldUpdate_whenStudentExists() {
        Student alice = service.addStudent("Alice", 90);
        service.updateScoreByIdOrThrow(alice.getId(), 100);
        int score = service.findById(alice.getId())
                .orElseThrow()
                .getScore();
        assertEquals(100, score);
    }

    @Test
    void updateScoreByIdOrThrow_shouldThrow_whenStudentNotExists() {
        assertThrows(NoSuchElementException.class, () -> service.updateScoreByIdOrThrow("S999", 100));
    }
}
