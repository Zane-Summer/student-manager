package zane;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zane.repository.StudentRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentService service;

    private List<Student> mockStudents;

    @BeforeEach
    void setUp() {
        mockStudents = Arrays.asList(
                new Student("S001", "Alice", 90),
                new Student("S002", "Bob", 75),
                new Student("S003", "Charlie", 55),
                new Student("S004", "Diana", 90)
        );
    }

    // ── addStudent ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("Should save and return a new student")
    void addStudent_shouldSaveAndReturnStudent() {
        Student result = service.addStudent("Alice", 90);

        assertEquals("Alice", result.getName());
        assertEquals(90, result.getScore());
        assertNotNull(result.getId());
        verify(repository, times(1)).save(result);
    }

    @Test
    @DisplayName("Should generate incremental IDs for each student added")
    void addStudent_shouldGenerateIncrementalIds() {
        Student s1 = service.addStudent("Alice", 90);
        Student s2 = service.addStudent("Bob", 80);

        assertNotEquals(s1.getId(), s2.getId());
    }

    // ── findById ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Should return student when ID exists")
    void findById_shouldReturnStudent_whenExists() {
        when(repository.findById("S001")).thenReturn(Optional.of(mockStudents.get(0)));

        Optional<Student> result = service.findById("S001");

        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().getName());
    }

    @Test
    @DisplayName("Should return empty when ID does not exist")
    void findById_shouldReturnEmpty_whenNotFound() {
        when(repository.findById("S999")).thenReturn(Optional.empty());

        Optional<Student> result = service.findById("S999");

        assertTrue(result.isEmpty());
    }

    // ── removeStudentsBelowScore ─────────────────────────────────────────────

    @Test
    @DisplayName("Should remove students below threshold and return count")
    void removeStudentsBelowScore_shouldRemoveAndReturnCount() {
        when(repository.findAll()).thenReturn(mockStudents);

        int removed = service.removeStudentsBelowScore(80);

        assertEquals(2, removed); // Bob(75) and Charlie(55)
        verify(repository).deleteById("S002");
        verify(repository).deleteById("S003");
    }

    @Test
    @DisplayName("Should return 0 when no students are below threshold")
    void removeStudentsBelowScore_shouldReturnZero_whenNoneMatch() {
        when(repository.findAll()).thenReturn(mockStudents);

        int removed = service.removeStudentsBelowScore(0);

        assertEquals(0, removed);
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Should throw when threshold is negative")
    void removeStudentsBelowScore_shouldThrow_whenThresholdNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> service.removeStudentsBelowScore(-1));
        verify(repository, never()).findAll();
    }

    // ── getStudentCount ──────────────────────────────────────────────────────

    @Test
    @DisplayName("Should return correct student count")
    void getStudentCount_shouldReturnCorrectCount() {
        when(repository.findAll()).thenReturn(mockStudents);

        assertEquals(4, service.getStudentCount());
    }

    @Test
    @DisplayName("Should return 0 when no students exist")
    void getStudentCount_shouldReturnZero_whenEmpty() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        assertEquals(0, service.getStudentCount());
    }

    // ── getStudentsBelowScore ────────────────────────────────────────────────

    @Test
    @DisplayName("Should return students below threshold")
    void getStudentsBelowScore_shouldReturnMatchingStudents() {
        when(repository.findAll()).thenReturn(mockStudents);

        List<Student> result = service.getStudentsBelowScore(80);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(s -> s.getScore() < 80));
    }

    @Test
    @DisplayName("Should return empty list when no students are below threshold")
    void getStudentsBelowScore_shouldReturnEmpty_whenNoneMatch() {
        when(repository.findAll()).thenReturn(mockStudents);

        List<Student> result = service.getStudentsBelowScore(0);

        assertTrue(result.isEmpty());
    }

    // ── updateScoreById ──────────────────────────────────────────────────────

    @Test
    @DisplayName("Should update score and return true when student exists")
    void updateScoreById_shouldReturnTrue_whenStudentExists() {
        when(repository.findById("S001")).thenReturn(Optional.of(mockStudents.get(0)));

        boolean updated = service.updateScoreById("S001", 100);

        assertTrue(updated);
        assertEquals(100, mockStudents.get(0).getScore());
    }

    @Test
    @DisplayName("Should return false when student does not exist")
    void updateScoreById_shouldReturnFalse_whenNotFound() {
        when(repository.findById("S999")).thenReturn(Optional.empty());

        boolean updated = service.updateScoreById("S999", 100);

        assertFalse(updated);
    }

    // ── updateScoreByIdOrThrow ───────────────────────────────────────────────

    @Test
    @DisplayName("Should update score without exception when student exists")
    void updateScoreByIdOrThrow_shouldUpdate_whenStudentExists() {
        when(repository.findById("S001")).thenReturn(Optional.of(mockStudents.get(0)));

        assertDoesNotThrow(() -> service.updateScoreByIdOrThrow("S001", 100));
        assertEquals(100, mockStudents.get(0).getScore());
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when student not found")
    void updateScoreByIdOrThrow_shouldThrow_whenNotFound() {
        when(repository.findById("S999")).thenReturn(Optional.empty());

        assertThrows(java.util.NoSuchElementException.class,
                () -> service.updateScoreByIdOrThrow("S999", 100));
    }

    // ── getStudentViewList ───────────────────────────────────────────────────

    @Test
    @DisplayName("Should return formatted name-score strings")
    void getStudentViewList_shouldReturnFormattedStrings() {
        when(repository.findAll()).thenReturn(List.of(
                new Student("S001", "Alice", 90)
        ));

        List<String> views = service.getStudentViewList();

        assertEquals(1, views.size());
        assertEquals("Alice - 90", views.get(0));
    }

    // ── getAllStudentViews ───────────────────────────────────────────────────

    @Test
    @DisplayName("Should return StudentView list mapped from students")
    void getAllStudentViews_shouldReturnMappedViews() {
        when(repository.findAll()).thenReturn(mockStudents);

        List<StudentView> views = service.getAllStudentViews();

        assertEquals(4, views.size());
        assertEquals("Alice", views.get(0).name());
        assertEquals(90, views.get(0).score());
    }

    // ── getTopNStudents ──────────────────────────────────────────────────────

    @Test
    @DisplayName("Should return top N students sorted by score desc, then name asc")
    void getTopNStudents_shouldReturnSortedList() {
        when(repository.findAll()).thenReturn(mockStudents);

        List<Student> top2 = service.getTopNStudents(2);

        assertEquals(2, top2.size());
        assertEquals("Alice", top2.get(0).getName());  // 90, Alice < Diana alphabetically
        assertEquals("Diana", top2.get(1).getName());  // 90, Diana
    }

    @Test
    @DisplayName("Should return all students when N exceeds total count")
    void getTopNStudents_shouldReturnAll_whenNExceedsSize() {
        when(repository.findAll()).thenReturn(mockStudents);

        List<Student> result = service.getTopNStudents(100);

        assertEquals(4, result.size());
    }

    @Test
    @DisplayName("Should return empty list when no students exist")
    void getTopNStudents_shouldReturnEmpty_whenNoStudents() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Student> result = service.getTopNStudents(3);

        assertTrue(result.isEmpty());
    }
}