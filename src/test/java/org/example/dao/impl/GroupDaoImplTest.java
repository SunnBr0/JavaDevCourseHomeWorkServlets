package org.example.dao.impl;

import org.example.dao.mapper.GroupResultSetMapper;
import org.example.db.ConnectionManager;
import org.example.entity.Groups;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class GroupDaoImplTest {
    private String dbProp = "sfdfdb.properties";

    @InjectMocks
    GroupDaoImpl groupDao = new GroupDaoImpl(dbProp);
    @Mock
    private Connection connection;
    @Mock
    ConnectionManager connectionManager;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private GroupResultSetMapper groupResultSetMapper;


    @Test
    void saveTestPositive() throws SQLException {
        Groups group = new Groups(1, "Logica", 10);

        when(connectionManager.getConnection(anyString())).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        groupDao.save(group);

        verify(connectionManager, times(1)).getConnection(anyString());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setString(1, group.getFaculty());
        verify(preparedStatement, times(1)).setInt(2, group.getNumberOfStudents());
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void saveShouldThrowExceptionWhenConnectionIsFailed() throws SQLException {
        Groups group = new Groups(1, "Logica", 10);

        when(connectionManager.getConnection(anyString())).thenThrow(new SQLException("Ошибка при сохранении студента в базу данных"));

        assertThrows(RuntimeException.class, () -> groupDao.save(group));
        verify(connectionManager, times(1)).getConnection(anyString());
    }
    @Test
    void saveShouldThrowExceptionWhenGroupDoesntExist() throws SQLException {
        Groups group = new Groups(1, "Logica", 10);

        assertThrows(RuntimeException.class, () -> groupDao.save(group));
    }

    @Test
    void updateTestPositive() throws SQLException {
        Groups group = new Groups(1, "Logica", 10);

        when(connectionManager.getConnection(anyString())).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        groupDao.update(group);

        verify(connectionManager, times(1)).getConnection(anyString());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setString(1, group.getFaculty());
        verify(preparedStatement, times(1)).setInt(2, group.getNumberOfStudents());
        verify(preparedStatement, times(1)).setLong(3, group.getId());
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void updateShouldThrowExceptionWhenUpdatingIsFailing() throws SQLException {
        Groups group = new Groups(1, "Logica", 10);

        when(connectionManager.getConnection(anyString())).thenThrow(new SQLException());

        assertThrows(RuntimeException.class, () -> groupDao.update(group));
        verify(connectionManager, times(1)).getConnection(anyString());
    }

    @Test
    void deleteTestPositive() throws SQLException {
        Groups group = new Groups(1, "Logica", 10);

        when(connectionManager.getConnection(anyString())).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

       groupDao.delete(group.getId());

        verify(connectionManager, times(1)).getConnection(anyString());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setLong(1, group.getId());
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void deleteShouldThrowWhenDeletingIsFailed() throws SQLException {
        Groups group = new Groups(1, "Logica", 10);

        when(connectionManager.getConnection(anyString())).thenThrow(new  SQLException());

        assertThrows(RuntimeException.class, () ->groupDao.delete(group.getId()));
        verify(connectionManager, times(1)).getConnection(anyString());
    }

    @Test
    void getTestPositive() throws SQLException {
        Groups group = new Groups(1, "Logica", 10);

        when(connectionManager.getConnection(anyString())).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(groupResultSetMapper.map(resultSet)).thenReturn(group);

        Groups actual = groupDao.get(group.getId());

        assertNotNull(actual);
        assertEquals(group, actual);
        verify(connectionManager, times(1)).getConnection(anyString());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setLong(1, group.getId());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(2)).next();
        verify(groupResultSetMapper, times(1)).map(resultSet);
    }

    @Test
    void getShouldReturnNullWhenStudentDoesntExist() throws SQLException {
        Groups group = new Groups(1, "Logica", 10);

        when(connectionManager.getConnection(anyString())).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Groups actual = groupDao.get(group.getId());

        assertNull(actual);
        verify(connectionManager, times(1)).getConnection(anyString());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setLong(1, group.getId());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verifyNoMoreInteractions(resultSet);
        verifyNoInteractions(groupResultSetMapper);
    }

    @Test
    void getShouldThrowExceptionWhenConnectionIsClosed() throws SQLException {
        Groups group = new Groups(1, "Logica", 10);

        when(connectionManager.getConnection(anyString())).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(new SQLException("Error executing query"));

        assertThrows(RuntimeException.class, () -> groupDao.get(group.getId()));

        verify(connectionManager, times(1)).getConnection(anyString());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setLong(1, group.getId());
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void getAllTestPositive() throws SQLException {
        List<Groups> groups = new ArrayList<>();
        Groups group = new Groups(1, "Logica", 10);
        groups.add(group);

        when(connectionManager.getConnection(anyString())).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(groupResultSetMapper.map(resultSet)).thenReturn(group);

        List<Groups> actual = groupDao.getAll();

        assertNotNull(groups);
        assertEquals(groups, actual);
        verify(connectionManager, times(1)).getConnection(anyString());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(2)).next();
        verify(groupResultSetMapper, times(1)).map(resultSet);
    }

    @Test
    void getAllShouldReturnEmptyList() throws SQLException {
        List<Groups> groups = new ArrayList<>();

        when(connectionManager.getConnection(anyString())).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        List<Groups> actual = groupDao.getAll();

        assertEquals(groups.size(), actual.size());
        verify(connectionManager, times(1)).getConnection(anyString());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verifyNoMoreInteractions(resultSet);
        verifyNoInteractions(groupResultSetMapper);
    }

    @Test
    void getAllShouldThrowExceptionWhenConnectionIsClosed() throws SQLException {
        when(connectionManager.getConnection(anyString())).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(new SQLException());

        assertThrows(RuntimeException.class, () -> groupDao.getAll());
        verify(connectionManager, times(1)).getConnection(anyString());
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
    }
}