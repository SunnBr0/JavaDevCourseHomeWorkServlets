package org.example.dto.mapper;

import org.example.dto.StudentDto;
import org.example.entity.Student;
import org.mapstruct.Mapper;

@Mapper
public interface StudentDtoMapper {

    Student mapToStudent(StudentDto studentDto);

    StudentDto mapToStudentDto(Student student);

}
