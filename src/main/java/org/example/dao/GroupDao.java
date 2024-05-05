package org.example.dao;

import org.example.entity.Groups;

import java.util.List;

public interface GroupDao {
    void save(Groups groups);
    void update(Groups groups);
    void delete(long id);
    Groups get(long id);
    List<Groups> getAll();
}
