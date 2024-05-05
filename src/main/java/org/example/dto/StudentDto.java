package org.example.dto;

import java.util.Objects;

public class StudentDto {
    private long id;
    private String name;
    private int age;
    private long groupId;

    public StudentDto() {
    }

    public StudentDto(long id, String name, int age, long groupId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.groupId = groupId;
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

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        StudentDto that = (StudentDto) object;
        return id == that.id && age == that.age && groupId == that.groupId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, groupId);
    }
}
