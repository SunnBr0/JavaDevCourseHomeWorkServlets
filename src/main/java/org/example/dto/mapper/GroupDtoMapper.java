package org.example.dto.mapper;

import org.example.dto.GroupDto;
import org.example.entity.Groups;
import org.mapstruct.Mapper;

@Mapper
public interface GroupDtoMapper {
    Groups mapToGroup(GroupDto groupDto);

    GroupDto mapToGroupDto(Groups group);
}
