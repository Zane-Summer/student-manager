package com.zane.student_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class StudentResponse {
    private  Long id;

    private String name;

    private int score;

}
