package zane.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import zane.Student;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class JsonFileStudentRepository implements StudentRepository {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, Student> studentMap = new HashMap<>();
    private final Path filePath = Paths.get("Students.json");

    // Read students.json when starting
    public JsonFileStudentRepository() {
        if (Files.notExists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create data file", e);
            }
        } else {
            try {
                List<Student> students = objectMapper.readValue(
                        Files.readAllBytes(filePath),
                        new TypeReference<List<Student>>() {
                        }
                );
                for (Student s : students) {
                    studentMap.put(s.getId(), s);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to read students.json", e);
            }
        }
    }

    @Override
    public void save(Student s) {
        studentMap.put(s.getId(), s);
        writeToFile();
    }

    @Override
    public Optional<Student> findById(String id) {
        return Optional.ofNullable(studentMap.get(id));
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(studentMap.values());
    }

    @Override
    public void deleteById(String id) {
        studentMap.remove(id);
        writeToFile();
    }

    private void writeToFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(filePath.toFile(), new ArrayList<>(studentMap.values()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to students.json", e);
        }
    }
}
