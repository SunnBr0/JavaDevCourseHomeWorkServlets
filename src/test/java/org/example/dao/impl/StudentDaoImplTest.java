package org.example.dao.impl;

import org.example.dao.mapper.StudentResultSetMapper;
import org.example.db.ConnectionManager;
import org.example.entity.Groups;
import org.example.entity.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentDaoImplTest {
    private String dbProp = "sfdfdb.properties";
    @InjectMocks
    private StudentDaoImpl studentDao = new StudentDaoImpl(dbProp);
    @Mock
    private Connection connection;
    @Mock
    ConnectionManager connectionManager;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private StudentResultSetMapper studentResultSetMapper;

    @Test
    void saveTestPositive() throws SQLException {
        Groups group = new Groups(1, "Physical", 10);
        Student student = new Student(1L, "Van", 120, (int) group.getId());

        when(connectionManager.getConnection(anyString())).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        studentDao.save(student);

        verify(connectionManager, times(1)).getConnection(anyString());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setString(1, student.getName());
        verify(preparedStatement, times(1)).setLong(2, student.getAge());
        verify(preparedStatement, times(1)).setLong(3, student.getGroup().getId());
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldThrowExceptionWhenConnectionIsFailed() throws SQLException {
        Groups group = new Groups(1, "Physical", 10);
        Student student = new Student(1L, "Van", 120, (int) group.getId());

        when(connectionManager.getConnection(anyString())).thenThrow(new SQLException("Ошибка при сохранении студента в базу данных"));

        assertThrows(RuntimeException.class, () -> studentDao.save(student));
        verify(connectionManager, times(1)).getConnection(anyString());
    }
    @Test
    void shouldThrowExceptionWhenGroupDoesntExist() throws SQLException {
        Student student = new Student(1L, "Van", 120,1);

        assertThrows(RuntimeException.class, () -> studentDao.save(student));
    }

    @Test
    void updateTestPositive() throws SQLException {
        Student student = new Student(1L, "Van", 120,1);

        when(connectionManager.getConnection(anyString())).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        studentDao.update(student);

        verify(connectionManager, times(1)).getConnection(anyString());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setString(1, student.getName());
        verify(preparedStatement, times(1)).setLong(2, student.getAge());
        verify(preparedStatement, times(1)).setLong(3, student.getGroup().getId());
        verify(preparedStatement, times(1)).setLong(4, student.getId());
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldThrowExceptionWhenUpdatingIsFailing() throws SQLException {
        Student student = new Student(1L, "Van", 120,1);

        when(connectionManager.getConnection(anyString())).thenThrow(new SQLException());

        assertThrows(RuntimeException.class, () -> studentDao.update(student));
        verify(connectionManager, times(1)).getConnection(anyString());
    }

    @Test
    void deleteTestPositive() throws SQLException {
        Student student = new Student(1L, "Van", 120,1);

        when(connectionManager.getConnection(anyString())).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        studentDao.delete(student.getId());

        verify(connectionManager, times(1)).getConnection(anyString());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setLong(1, student.getId());
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void shouldThrowWhenDeletingIsFailed() throws SQLException {
        Student student = new Student(1L, "Van", 120,1);

        when(connectionManager.getConnection(anyString())).thenThrow(new  SQLException());

        assertThrows(RuntimeException.class, () -> studentDao.delete(student.getId()));
        verify(connectionManager, times(1)).getConnection(anyString());
    }

    @Test
    void getTestPositive() throws SQLException {
        Student student = new Student(1L, "Van", 120,1);

        when(connectionManager.getConnection(anyString())).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(studentResultSetMapper.map(resultSet)).thenReturn(student);

        Student actual = studentDao.get(student.getId());

        assertNotNull(actual);
        assertEquals(student, actual);
        verify(connectionManager, times(1)).getConnection(anyString());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(studentResultSetMapper, times(1)).map(resultSet);
    }

    @Test
    void getShouldReturnNullWhenStudentDoestnExist() throws SQLException {
        Student student = new Student(1L, "Van", 120,1);

        when(connectionManager.getConnection(anyString())).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Student actual = studentDao.get(student.getId());

        assertNull(actual);
        verify(connectionManager, times(1)).getConnection(anyString());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setLong(1, student.getId());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verifyNoMoreInteractions(resultSet);
        verifyNoInteractions(studentResultSetMapper);
    }

    @Test
    void getShouldThrowExceptionWhenConnectionIsClosed() throws SQLException {
        Student student = new Student(1L, "Van", 120,1);

        when(connectionManager.getConnection(anyString())).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error executing query"));

        assertThrows(RuntimeException.class, () -> studentDao.get(student.getId()));

        verify(connectionManager, times(1)).getConnection(anyString());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setLong(1, student.getId());
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void getAllTestPositive() throws SQLException {
        List<Student> students = new ArrayList<>();
        Student student = new Student(1L, "Van", 120,1);
        students.add(student);

        when(connectionManager.getConnection(anyString())).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(studentResultSetMapper.map(resultSet)).thenReturn(student);

        List<Student> actual = studentDao.getAll();

        assertNotNull(students);
        assertEquals(students, actual);
        verify(connectionManager, times(1)).getConnection(anyString());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(2)).next();
        verify(studentResultSetMapper, times(1)).map(resultSet);
    }

    @Test
    void getAllShouldReturnEmptyList() throws SQLException {
        List<Student> students = new ArrayList<>();

        when(connectionManager.getConnection(anyString())).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        List<Student> actual = studentDao.getAll();

        assertEquals(students.size(), actual.size());
        verify(connectionManager, times(1)).getConnection(anyString());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verifyNoInteractions(studentResultSetMapper);
    }

    @Test
    void getAllShouldThrowExceptionWhenConnectionIsClosed() throws SQLException {

        when(connectionManager.getConnection(anyString())).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(new SQLException());

        assertThrows(RuntimeException.class, () -> studentDao.getAll());
        verify(connectionManager, times(1)).getConnection(anyString());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
    }
}