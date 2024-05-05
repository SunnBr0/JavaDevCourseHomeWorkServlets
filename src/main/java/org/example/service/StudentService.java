package org.example.service;

import org.example.dao.StudentDao;
import org.example.dto.StudentDto;

import java.util.List;

public interface StudentService {
    void saveStudent(StudentDto studentDto);
    void updateStudent(StudentDto studentDto);
    void deleteStudent(long studentID);
    StudentDto getStudentById(long studentId);
    List<StudentDto> getAllStudents();
}
