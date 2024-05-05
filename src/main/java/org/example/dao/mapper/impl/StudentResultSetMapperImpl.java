package org.example.dao.mapper.impl;

import org.example.dao.mapper.StudentResultSetMapper;
import org.example.entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentResultSetMapperImpl implements StudentResultSetMapper {
    @Override
    public Student map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        int groupId = resultSet.getInt("groups_id");

        return new Student(id, name, age, groupId);
    }
}
