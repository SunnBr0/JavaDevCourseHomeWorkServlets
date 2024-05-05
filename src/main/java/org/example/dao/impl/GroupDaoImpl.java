package org.example.dao.impl;

import org.example.dao.GroupDao;
import org.example.dao.mapper.GroupResultSetMapper;
import org.example.dao.mapper.impl.GroupResultSetMapperImpl;
import org.example.db.ConnectionManager;
import org.example.entity.Groups;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDaoImpl implements GroupDao {

    private String dbProp;

    private GroupResultSetMapper groupResultSetMapper = new GroupResultSetMapperImpl();

    private ConnectionManager connectionManager = new ConnectionManager();

    public GroupDaoImpl(String dbProp) {
        this.dbProp = dbProp;
    }

    @Override
    public void save(Groups group) {
        String saveSQL = """
                INSERT INTO groups (faculty, number_of_students)
                values (?, ?)
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement statement = connection.prepareStatement(saveSQL)) {

            statement.setString(1, group.getFaculty());
            statement.setInt(2, group.getNumberOfStudents());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка в сохранении группы в базу данных", e);
        }
    }

    @Override
    public void update(Groups group) {
        String updateSQL = """
                UPDATE groups 
                SET faculty = ?, 
                number_of_students = ?
                WHERE ID = ?
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, group.getFaculty());
            preparedStatement.setInt(2, group.getNumberOfStudents());
            preparedStatement.setLong(3, group.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении группы в базу данных", e);
        }
    }

    @Override
    public void delete(long id) {
        String deleteSQL = """
                DELETE FROM GROUPS
                WHERE ID = ?
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement statement = connection.prepareStatement(deleteSQL)) {

            statement.setLong(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении группы из базы данных", e);
        }

    }

    @Override
    public Groups get(long id) {
        Groups group = null;

        String getSQL = """
                SELECT id, faculty, number_of_students
                FROM groups
                WHERE id = ?
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement preparedStatement = connection.prepareStatement(getSQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                group = groupResultSetMapper.map(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении группы из базы данных", e);
        }
        return group;
    }

    @Override
    public List<Groups> getAll() {
        List<Groups> groups = new ArrayList<>();

        String getAllSQL = """
                SELECT id, faculty, number_of_students
                FROM GROUPS
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement statement = connection.prepareStatement(getAllSQL)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                groups.add(groupResultSetMapper.map(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении групп из базы данных", e);
        }
        return groups;
    }
}
