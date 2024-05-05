package org.example.dto;

import java.util.Objects;

public class GroupDto {
    private long id;

    private String faculty;

    private int numberOfStudents;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        GroupDto groupDto = (GroupDto) object;
        return id == groupDto.id && numberOfStudents == groupDto.numberOfStudents && Objects.equals(faculty, groupDto.faculty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, faculty, numberOfStudents);
    }
}
