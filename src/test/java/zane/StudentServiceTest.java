package zane;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zane.repository.InMemoryStudentRepository;

import java.util.List;
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

    @Test
    void getStudentsBelowScore_shouldReturnFilteredList() {
        service.addStudent("Alice", 90);
        service.addStudent("Bob", 70);
        service.addStudent("Charlie", 60);

        List<Student> lowScorers = service.getStudentsBelowScore(60);
        assertEquals(1, lowScorers.size());
        assertEquals("Charlie", lowScorers.get(0).getName());
    }

    @Test
    void getStudentViews_shouldReturnCorrectMapping() {
        service.addStudent("Alice", 90);
        service.addStudent("Bob", 70);

        List<StudentView> views = service.getAllStudentViews();
        assertEquals(2, views.size());
        assertEquals("Alice", views.get(0).name());
        assertEquals(90, views.get(0).score());
        assertEquals("Bob", views.get(1).name());
        assertEquals(70, views.get(1).score());
    }

    @Test
    void getStudentViewList_shouldReturnCorrectFormat() {
        service.addStudent("Alice", 90);
        service.addStudent("Bob", 70);

        List<String> viewList = service.getStudentViewList();
        assertEquals(2, viewList.size());
        assertEquals("Alice - 90", viewList.get(0));
        assertEquals("Bob - 70", viewList.get(1));
    }

}
