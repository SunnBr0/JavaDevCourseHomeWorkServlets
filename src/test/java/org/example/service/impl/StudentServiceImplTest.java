package org.example.service.impl;

import org.example.dao.impl.StudentDaoImpl;
import org.example.dto.StudentDto;
import org.example.dto.mapper.StudentDtoMapperImpl;
import org.example.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentDaoImpl studentDao;

    @Mock
    StudentDtoMapperImpl studentDtoMapper;

    Student student = new Student();
    StudentDto studentDto = new StudentDto();


    @Test
    void saveStudentTest() {
        Mockito.when(studentDtoMapper.mapToStudent(studentDto)).thenReturn(student);

        studentService.saveStudent(studentDto);

        Mockito.verify(studentDao, Mockito.times(1)).save(student);
    }

    @Test
    void updateStudentTest() {
        Mockito.when(studentDtoMapper.mapToStudent(studentDto)).thenReturn(student);

        studentService.updateStudent(studentDto);

        Mockito.verify(studentDao, Mockito.times(1)).update(student);
    }

    @Test
    void deleteStudentTest() {
        studentService.deleteStudent(1);

        Mockito.verify(studentDao, Mockito.times(1)).delete(1);
    }

    @Test
    void getStudentByIdTest() {
        Mockito.when(studentDao.get(1)).thenReturn(student);
        Mockito.when(studentDtoMapper.mapToStudentDto(student)).thenReturn(studentDto);

        StudentDto actual = studentService.getStudentById(1);

        Assertions.assertEquals(studentDto, actual);
    }

    @Test
    void getAllStudentsTest() {
        List<Student> students = new ArrayList<>();
        students.add(student);
        Student student1 = new Student();
        students.add(student1);

        Mockito.when(studentDao.getAll()).thenReturn(students);

        List<StudentDto> expected = new ArrayList<>();
        expected.add(studentDto);
        StudentDto studentDto1 = new StudentDto();
        expected.add(studentDto1);

        Mockito.when(studentDtoMapper.mapToStudentDto(student)).thenReturn(studentDto);
        Mockito.when(studentDtoMapper.mapToStudentDto(student1)).thenReturn(studentDto1);

        List<StudentDto> actual = studentService.getAllStudents();

        Assertions.assertEquals(expected, actual);
    }
}