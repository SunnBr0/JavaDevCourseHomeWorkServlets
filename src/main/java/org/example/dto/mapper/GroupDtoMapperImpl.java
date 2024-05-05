package org.example.dto.mapper;

import org.example.dto.GroupDto;
import org.example.entity.Groups;

public class GroupDtoMapperImpl implements GroupDtoMapper{
    @Override
    public Groups mapToGroup(GroupDto groupDto) {
        Groups group = new Groups();
        group.setId(groupDto.getId());
        group.setFaculty(groupDto.getFaculty());
        group.setNumberOfStudents(groupDto.getNumberOfStudents());

        return group;
    }

    @Override
    public GroupDto mapToGroupDto(Groups group) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setFaculty(group.getFaculty());
        groupDto.setNumberOfStudents(group.getNumberOfStudents());
        return groupDto;
    }
}
