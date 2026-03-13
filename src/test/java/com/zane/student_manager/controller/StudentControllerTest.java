package com.zane.student_manager.controller;

import com.zane.student_manager.dto.StudentResponse;
import com.zane.student_manager.exception.StudentNotFoundException;
import com.zane.student_manager.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService service;

    @Test
    void getStudentById_shouldReturn200_whenExists() throws Exception {
        StudentResponse resp = new StudentResponse(1L, "Zane", 99);
        when(service.findById(1L)).thenReturn(resp);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Zane"))
                .andExpect(jsonPath("$.score").value(99));
    }

    @Test
    void getStudentById_shouldReturn404_whenNotFound() throws Exception {
        when(service.findById(1L)).thenThrow(new StudentNotFoundException(1L));

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addStudentById_shouldReturn400_whenScoreInValid() throws Exception {
        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Zane\", \"score\": 150}"))
                .andExpect(status().isBadRequest()
        );
    }
}
