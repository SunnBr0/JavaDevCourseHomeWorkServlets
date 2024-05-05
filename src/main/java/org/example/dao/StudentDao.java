package org.example.dao;

import org.example.entity.Student;

import java.util.List;

public interface StudentDao {

    void save(Student student);
    void update(Student student);
    void delete(long id);
    Student get(long id);
    List<Student> getAll();
}
