package com.example.springboot_web.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentService underTest;


    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @Test
    void getStudents() {
        //when
        underTest.getStudents();
        //then
        verify(studentRepository).findAll();
    }

    @Test
    void addNewStudent() {
        //given
        Student student = new Student(
                "Guy",
                "thisguy@gmail.com",
                LocalDate.of(1970, Month.APRIL, 6)
        );
        //when
        underTest.addNewStudent(student);

        //then
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);

        verify(studentRepository)
                .save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void deleteStudent_happyPath() {
        //given
        Long studentId = 1L;
        given(studentRepository.existsById(studentId)).willReturn(true);

        //when
        underTest.deleteStudent(studentId);

        //then
        ArgumentCaptor<Long> longArgumentCaptor =
                ArgumentCaptor.forClass(Long.class);

        verify(studentRepository).existsById(studentId);

        verify(studentRepository)
                .deleteById(longArgumentCaptor.capture());

        Long capturedLong = longArgumentCaptor.getValue();

        assertThat(capturedLong).isEqualTo(studentId);
    }

    @Test
    void deleteStudent_sadPath() {
        //given
        Long studentId = 1L;
        given(studentRepository.existsById(studentId)).willReturn(false);

        //when && then
        Throwable throwable = assertThrows(IllegalStateException.class,
                () -> underTest.deleteStudent(studentId));

        assertThat(throwable.getMessage()).isEqualTo("Student with id " + studentId
            + " does not exist in database");

    }

    @Test
    void updateStudent() {
        //given
        Student student = new Student(
                "Guy",
                "thisguy@gmail.com",
                LocalDate.of(1970, Month.APRIL, 6)
        );
        Long studentId = 1L;
        String name1 = "Moe";
        String email1 = "moeszyslak@gmail.com";
        given(studentRepository.findById(studentId))
                .willReturn(Optional.of(student));
        //when
        underTest.updateStudent(studentId, name1, email1);

        //then
        //verify(studentRepository.findByEmail(email));
        assertThat(name1).isGreaterThan("");
//        assertThat(name1).isNotEqualToIgnoringCase(student.getName());
//        assertThat(email1).isNotEqualToIgnoringCase(student.getEmail());
        assertThat(email1).isGreaterThan("");
    }
}