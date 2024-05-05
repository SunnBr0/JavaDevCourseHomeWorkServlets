package org.example.dao.mapper;

import org.example.entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface StudentResultSetMapper {
    Student map (ResultSet resultSet) throws SQLException;
}
