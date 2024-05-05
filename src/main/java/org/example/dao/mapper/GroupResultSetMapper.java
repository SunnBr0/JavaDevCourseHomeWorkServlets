package org.example.dao.mapper;

import org.example.entity.Groups;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface GroupResultSetMapper {
    Groups map (ResultSet resultset) throws SQLException;
}
