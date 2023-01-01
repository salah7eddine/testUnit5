package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }




    @Test
    void itShouldCheckWhenStudentEmailExist() {
        // given
        String email = "mery@gmail.com";
        Student student = new Student(
                "Mery",
                email,
                Gender.FEMALE
        );
        underTest.save(student);

        // when
        Boolean expected = underTest.selectExistsEmail(email);

        // then
        assertThat(expected).isTrue();
    }

    @Test
    void findStudentByNameTest() {
        // given
        String name = "Mery";
        String email = "mery@gmail.com";
        Student student = new Student(
                name,
                email,
                Gender.FEMALE
        );
        underTest.save(student);
        // when
        Student studentSaved = underTest.findStudentByName(name);


        // then
        assertThat(studentSaved).isNotNull();
        assertThat(studentSaved.getName()).isEqualTo(name);
    }

    @Test
    void itShouldCheckWhenStudentEmailDoesNotExist() {

        // given
        String email = "meryy@gmail.com";

        // when
        Boolean expected = underTest.selectExistsEmail(email);

        // then
        assertThat(expected).isFalse();
    }
}