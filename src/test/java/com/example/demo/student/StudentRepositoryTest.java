package com.example.demo.student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void itShouldCheckIfStudentSelectExistsEmail() {
        // given
        Student student = new Student(
                "Meryem",
                "mery@gmail.com",
                Gender.FEMALE
        );
        studentRepository.save(student);

        // when
        Boolean expected = studentRepository.selectExistsEmail("mery@gmail.com");

        // then
        assertThat(expected).isTrue();
    }
}