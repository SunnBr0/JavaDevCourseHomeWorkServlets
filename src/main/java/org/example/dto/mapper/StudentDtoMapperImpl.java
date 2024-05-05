package org.example.dto.mapper;

import org.example.dao.GroupDao;
import org.example.dao.impl.GroupDaoImpl;
import org.example.dto.StudentDto;
import org.example.entity.Student;

public class StudentDtoMapperImpl implements StudentDtoMapper{
    String dbProp = "db.properties";

    GroupDao groupDao = new GroupDaoImpl(dbProp);
    @Override
    public Student mapToStudent(StudentDto studentDto) {
        Student student = new Student();
        student.setId(studentDto.getId());
        student.setName(studentDto.getName());
        student.setAge(studentDto.getAge());
        student.setGroup(groupDao.get(studentDto.getGroupId()));

        return student;
    }

    @Override
    public StudentDto mapToStudentDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setAge(student.getAge());
        studentDto.setGroupId(student.getGroup().getId());

        return studentDto;
    }
}
