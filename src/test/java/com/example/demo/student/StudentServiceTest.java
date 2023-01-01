package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock private StudentRepository studentRepository;
    //private AutoCloseable autoCloseable;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        //autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new StudentService(studentRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        //autoCloseable.close();
    }

    @Test
    void canGetAllStudents() {
        // When
        underTest.getAllStudents();
        // then
        verify(studentRepository).findAll();
    }

    @Test
    void canAddStudent() {
        // given
        String email = "mery@gmail.com";
        Student student = new Student(
                "Mery",
                email,
                Gender.FEMALE
        );

        // when
        underTest.addStudent(student);

        // then
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);

        verify(studentRepository)
                .save(studentArgumentCaptor.capture());

        Student capwturedStudent = studentArgumentCaptor.getValue();
        assertThat(capwturedStudent).isEqualTo(student);
    }

    @Test
    void willThrowWhenEmailsTaken() {
        // given
        String email = "mery@gmail.com";
        Student student = new Student(
                "Mery",
                email,
                Gender.FEMALE
        );

        given(studentRepository.selectExistsEmail(anyString()))
                .willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> underTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + student.getEmail() + " taken");

        verify(studentRepository, never()).save(any());
    }

    @Test
    void deleteStudent() {
        // given
        Long studentId = 1l;
        String email = "mery@gmail.com";
        Student student = new Student(
                studentId,
                "Mery",
                email,
                Gender.FEMALE
        );
        // when studentRepository.existsById(studentId)
        when(studentRepository.existsById(studentId)).thenReturn(true);
        underTest.deleteStudent(studentId);
        //then
        verify(studentRepository).deleteById(any()); // check that the method was called
    }

    @Test
    void deleteStudentNoExist() {
        // given
        Long studentId = 1l;
        String email = "mery@gmail.com";
        Student student = new Student(
                studentId,
                "Mery",
                email,
                Gender.FEMALE
        );
        given(studentRepository.existsById(anyLong()))
                .willReturn(false);
        // when
        // then
        assertThatThrownBy(() -> underTest.deleteStudent(studentId))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining( "Student with id " + studentId + " does not exists");
        verify(studentRepository, never()).deleteById(any());
    }
}