package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void itShouldCheckWhenStudentEmailExist() {
        // given
        String email = "mery@gmail.com";
        Student student = new Student(
                email,
                "mery@gmail.com",
                Gender.FEMALE
        );
        studentRepository.save(student);

        // when
        Boolean expected = studentRepository.selectExistsEmail(email);

        // then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckWhenStudentEmailDoesNotExist() {

        // given
        String email = "meryy@gmail.com";

        // when
        Boolean expected = studentRepository.selectExistsEmail(email);

        // then
        assertThat(expected).isFalse();
    }
}