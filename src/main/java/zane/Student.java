package zane;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Student {
    private final String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public void setScore(int score) {
        if (score >= 0 && score <= 100) {
            this.score = score;
        } else {
            System.out.println("Invalid score. Score must be between 0 and 100.");
        }
    }

}