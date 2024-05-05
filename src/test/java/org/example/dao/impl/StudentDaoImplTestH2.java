package org.example.dao.impl;


import org.example.db.ConnectionManager;
import org.example.entity.Groups;
import org.example.entity.Student;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentDaoImplTestH2 {
    private String dbProp = "h2.properties";

    private ConnectionManager connectionManager;

    private StudentDaoImpl studentDao;
    private GroupDaoImpl groupDao;

    @BeforeAll
    public void setupDatabase() {
        try {
            connectionManager = new ConnectionManager();

            Connection connection = connectionManager.getConnection(dbProp);
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE GROUPS (id INT PRIMARY KEY AUTO_INCREMENT, faculty VARCHAR(255), number_of_students INT)");
            statement.executeUpdate("CREATE TABLE STUDENT (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), age INT, groups_id INT, FOREIGN KEY (groups_id) REFERENCES groups(id))");

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании таблицы групп", e);
        }
    }

    @BeforeEach
    public void setup() {
        groupDao = new GroupDaoImpl(dbProp);
        studentDao = new StudentDaoImpl(dbProp);

        Groups group = new Groups();
        group.setFaculty("IT");
        group.setNumberOfStudents(20);
        groupDao.save(group);

    }

    @AfterAll
    public void teardownDatabase() {
        try {
            Connection connection = connectionManager.getConnection(dbProp);
            Statement statement = connection.createStatement();

            statement.executeUpdate("DROP TABLE student");
            statement.executeUpdate("DROP TABLE groups");

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении таблицы групп", e);
        }
    }

    @Test
    void saveShouldAddStudentToH2Database() {
        Groups savedGroup = groupDao.get(1L);

        Student student = new Student();
        student.setName("vova");
        student.setAge(10);
        student.setGroup(savedGroup);

        studentDao.save(student);

        Student test = studentDao.get(1L);

        assertNotNull(test);
        assertEquals(student.getName(), test.getName());

        try {
            Connection connection = connectionManager.getConnection(dbProp);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM student WHERE id = ?");
            statement.setLong(1, 1L);

            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next());

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при проверке сохранения группы в базу данных", e);
        }
    }

    @Test
    void updateShouldUpdateStudent() {
        Groups savedGroup = groupDao.get(1L);

        Student student = new Student();
        student.setName("vova");
        student.setAge(10);
        student.setGroup(savedGroup);

        studentDao.save(student);

        Student updStudent = new Student();
        updStudent.setId(1L);
        updStudent.setName("vova");
        updStudent.setAge(20);
        updStudent.setGroup(savedGroup);

        studentDao.update(updStudent);

        Student test = studentDao.get(1L);

        assertEquals(updStudent.getAge(), test.getAge());
        assertNotEquals(student.getAge(), test.getAge());
    }

    @Test
    void deleteShouldDeleteStudent() {
        Groups savedGroup = groupDao.get(1L);

        Student student = new Student();
        student.setName("vova");
        student.setAge(10);
        student.setGroup(savedGroup);

        studentDao.save(student);

        studentDao.delete(1L);

        assertNull(studentDao.get(1L));
    }

    @Test
    void getShouldReturnStudent() {
        Groups savedGroup = groupDao.get(1L);

        Student student = new Student();
        student.setName("vova");
        student.setAge(10);
        student.setGroup(savedGroup);

        studentDao.save(student);

        Student actual = studentDao.get(1L);

        assertEquals(student.getName(), actual.getName());
    }

    @Test
    void deleteShouldThrowExceptionWhenConnectionIsClosed() throws SQLException {
        assertThrows(SQLException.class, () -> connectionManager.getConnection("fail.properties"));
    }
}