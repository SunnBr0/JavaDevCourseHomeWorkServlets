package org.example.entity;

import org.example.dao.GroupDao;
import org.example.dao.impl.GroupDaoImpl;

import java.util.Objects;

public class Student {
    private long id;

    private String name;

    private int age;

    private Groups group;
    private String dbProp = "db.properties";

    private GroupDao groupDao = new GroupDaoImpl(dbProp);

    public Student(String name, int age, int groupId) {
        this.name = name;
        this.age = age;
        this.group = groupDao.get(groupId);
    }

    public Student(long id, String name, int age, int groupId){
        this.id = id;
        this.name = name;
        this.age = age;
        this.group = groupDao.get(groupId);
    }

    public Student() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Groups getGroup() {
        return group;
    }

    public void setGroup(Groups group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Student student = (Student) object;
        return id == student.id && age == student.age && Objects.equals(name, student.name) && Objects.equals(group, student.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, group);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", group=" + group +
                '}';
    }
}