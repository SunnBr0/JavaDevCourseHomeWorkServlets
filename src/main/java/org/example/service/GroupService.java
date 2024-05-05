package org.example.service;

import org.example.dto.GroupDto;
import org.example.entity.Groups;

import java.util.List;

public interface GroupService {
    void saveGroup(GroupDto groupDto);

    void updateGroup(GroupDto groupDto);

    void deleteGroup(long id);

    GroupDto getGroup(long id);

    List<GroupDto> getAllGroups();


}
