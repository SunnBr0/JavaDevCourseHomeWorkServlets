package org.example.dao.impl;

import org.example.db.ConnectionManager;
import org.example.entity.Groups;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GroupDaoImplTestH2 {

    private String dbProp = "h2.properties";

    private ConnectionManager connectionManager;
    private GroupDaoImpl groupDao;

    @BeforeAll
    public void setupDatabase() {
        try {
            connectionManager = new ConnectionManager();

            Connection connection = connectionManager.getConnection(dbProp);
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE groups (id INT PRIMARY KEY AUTO_INCREMENT, faculty VARCHAR(255), number_of_students INT)");

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка в создании таблицы групп", e);
        }
    }

    @BeforeEach
    public void setup() {
        groupDao = new GroupDaoImpl(dbProp);
    }

    @Test
    void saveShouldAddGroupToH2Database() {
        Groups group = new Groups();
        group.setFaculty("IT");
        group.setNumberOfStudents(20);

        groupDao.save(group);
        Groups test = groupDao.get(1L);

        assertNotNull(test);
        assertEquals(group.getFaculty(), test.getFaculty());

        try {
            Connection connection = connectionManager.getConnection(dbProp);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM groups WHERE id = ?");
            statement.setLong(1, 1L);

            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next());

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при проверке сохранения группы в базу данных", e);
        }
    }

    @Test
    void updateShouldUpdateGroup() {
        Groups group = new Groups();
        group.setFaculty("IT");
        group.setNumberOfStudents(20);
        groupDao.save(group);

        Groups updGroup = new Groups(1, "IT", 15);
        groupDao.update(updGroup);

        Groups test = groupDao.get(1L);

        assertEquals(updGroup.getNumberOfStudents(), test.getNumberOfStudents());
        assertNotEquals(group.getNumberOfStudents(), test.getNumberOfStudents());
    }

    @Test
    void deleteShouldDeleteGroup() {
        Groups group = new Groups();
        group.setFaculty("IT");
        group.setNumberOfStudents(20);
        groupDao.save(group);

        groupDao.delete(1L);

        assertNull(groupDao.get(1));
    }
    @Test
    void getShouldReturnGroup() {
        Groups group = new Groups();
        group.setFaculty("IT");
        group.setNumberOfStudents(20);
        groupDao.save(group);

        Groups actual = groupDao.get(1L);

        assertEquals(group.getFaculty(), actual.getFaculty());
    }

    @Test
    void getAllShouldReturnAllGroups() {
        List<Groups> groups = new ArrayList<>();
        Groups group = new Groups();
        group.setFaculty("IT");
        group.setNumberOfStudents(20);
        groups.add(group);

        Groups group1 = new Groups();
        group1.setFaculty("IT");
        group1.setNumberOfStudents(20);
        groups.add(group1);

        groupDao.save(group);
        groupDao.save(group1);

        List<Groups> actual = groupDao.getAll();

        assertEquals(groups.size(), actual.size());
    }

    @Test
    void deleteShouldThrowExceptionWhenConnectionIsClosed() throws SQLException {
        assertThrows(SQLException.class, () -> connectionManager.getConnection("fail.properties"));
    }

    @AfterAll
    public void teardownDatabase() {
        try {
            Connection connection = connectionManager.getConnection(dbProp);
            Statement statement = connection.createStatement();

            statement.executeUpdate("DROP TABLE groups");

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении таблицы групп", e);
        }
    }
}