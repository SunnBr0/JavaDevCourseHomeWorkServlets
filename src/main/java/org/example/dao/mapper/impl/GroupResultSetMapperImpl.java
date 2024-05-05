package org.example.dao.mapper.impl;

import org.example.dao.mapper.GroupResultSetMapper;
import org.example.entity.Groups;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupResultSetMapperImpl implements GroupResultSetMapper {


    @Override
    public Groups map(ResultSet resultSet) throws SQLException {
        int groupId = resultSet.getInt("id");
        String faculty = resultSet.getString("faculty");
        int numberOfStudents = resultSet.getInt("number_of_students");
        return new Groups(groupId, faculty, numberOfStudents);
    }
}
