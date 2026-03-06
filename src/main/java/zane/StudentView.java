package zane;

public record StudentView(String name, int score) {
    public StudentView(Student s) {
        this(s.getName(), s.getScore());
        }
    }
