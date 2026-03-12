package com.zane.student_manager;

import com.zane.student_manager.entity.Student;
import com.zane.student_manager.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner initData(StudentRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                System.out.println("Seeding initial student data...");

                Random random = new Random();
                String[] firstNames = {"James", "Mary", "Robert", "Patricia", "John", "Jennifer", "Michael", "Linda"};
                String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis"};

                for (int i = 0; i < 100; i++) {
                    String name = firstNames[random.nextInt(firstNames.length)] + " " +
                            lastNames[random.nextInt(lastNames.length)];
                    int score = random.nextInt(101);

                    Student s = new Student();
                    s.setName(name);
                    s.setScore(score);
                    repository.save(s);
                }

                System.out.println("Data seeding completed.");
            } else {
                System.out.println("Student data already exists. Skipping seeding.");
            }
        };
    }
}