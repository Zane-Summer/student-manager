package zane;

import lombok.Getter;
import lombok.ToString;

import java.util.Locale;

@Getter
@ToString
public class Student {
    private final String id;
    private String name;
    private int score;

    public Student(String id, String name, int score) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Student name cannot be blank");
        }
        this.id = id;
        this.name = name;
        setScore(score);
    }

    public void setScore(int score) {
        if (score < 0 || score > 100){
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }
    this.score = score;
    }
}