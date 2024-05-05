package org.example.service.impl;

import org.example.dao.StudentDao;
import org.example.dao.impl.StudentDaoImpl;
import org.example.dto.StudentDto;
import org.example.dto.mapper.StudentDtoMapper;
import org.example.entity.Student;
import org.example.service.StudentService;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {
    private String dbProp = "db.properties";

    private StudentDao studentDao = new StudentDaoImpl(dbProp);

    private StudentDtoMapper studentDtoMapper = Mappers.getMapper(StudentDtoMapper.class);


    @Override
    public void saveStudent(StudentDto studentDto) {
        Student student = studentDtoMapper.mapToStudent(studentDto);
        studentDao.save(student);
    }


    @Override
    public void updateStudent(StudentDto studentDto) {
        Student student = studentDtoMapper.mapToStudent(studentDto);
        studentDao.update(student);
    }

    @Override
    public void deleteStudent(long studentId) {
        studentDao.delete(studentId);
    }

    @Override
    public StudentDto getStudentById(long studentId) {
        Student student = studentDao.get(studentId);
        return studentDtoMapper.mapToStudentDto(student);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentDao.getAll();
        return students.stream()
                .map(student -> studentDtoMapper.mapToStudentDto(student))
                .collect(Collectors.toList());
    }
}
