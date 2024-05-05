package org.example.dao.impl;

import org.example.dao.StudentDao;
import org.example.dao.mapper.StudentResultSetMapper;
import org.example.dao.mapper.impl.StudentResultSetMapperImpl;
import org.example.db.ConnectionManager;
import org.example.entity.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    StudentResultSetMapper studentResultSetMapper = new StudentResultSetMapperImpl();
    private ConnectionManager connectionManager = new ConnectionManager();
    String dbProp = "db.properties";

    public StudentDaoImpl(String dbProp) {
        this.dbProp = dbProp;
    }

    @Override
    public void save(Student student) {

        String saveToDb = """
                INSERT INTO student (name, age, groups_id)
                VALUES (?, ?, ?);
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement statement = connection.prepareStatement(saveToDb)) {

            statement.setString(1, student.getName());
            statement.setLong(2, student.getAge());
            statement.setLong(3, student.getGroup().getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка в сохранении студента в базу данных", e);
        } catch (NullPointerException e) {
            throw new RuntimeException("Номер группы отсутствует", e);
        }
    }

    @Override
    public void update(Student student) {


        String updateDb = """
                UPDATE student
                SET name = ?,
                age = ?,
                groups_id = ?
                where id = ?
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
            PreparedStatement statement = connection.prepareStatement(updateDb)) {

            statement.setString(1, student.getName());
            statement.setLong(2, student.getAge());
            statement.setLong(3, student.getGroup().getId());
            statement.setLong(4, student.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка в редактировании студента в базу данных", e);
        }

    }

    @Override
    public void delete(long id) {
        String delete = """
                DELETE FROM student
                where id = ?;
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement statement = connection.prepareStatement(delete)) {

            statement.setLong(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка в удалении студента в базу данных", e);
        }

    }

    @Override
    public Student get(long studentId) {
        String get = """
                SELECT * 
                FROM student
                WHERE ID = ?
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement statement = connection.prepareStatement(get)) {

            statement.setLong(1, studentId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return studentResultSetMapper.map(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка в получении студента из базы данных", e);
        }

        return null;
    }

    @Override
    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();

        String getAllSQL = """
                SELECT id, name, age, groups_id
                FROM STUDENT
                """;

        try (Connection connection = connectionManager.getConnection(dbProp);
             PreparedStatement statement = connection.prepareStatement(getAllSQL);
            ResultSet resultSet = statement.executeQuery()){

            while (resultSet.next()) {
                students.add(studentResultSetMapper.map(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка в получении студентов из базы данных", e);
        }
        return students;
    }
}
