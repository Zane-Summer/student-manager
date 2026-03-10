package com.zane.student_manager.entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int score;

    public void setScore(int score) {
        if (score < 0 || score > 100){
            throw new IllegalArgumentException("Score must be between 0 and 100");
        }
        this.score = score;
    }
}