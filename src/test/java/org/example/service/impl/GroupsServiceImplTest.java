package org.example.service.impl;

import org.example.dao.impl.GroupDaoImpl;
import org.example.dto.GroupDto;
import org.example.dto.mapper.GroupDtoMapperImpl;
import org.example.entity.Groups;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class GroupsServiceImplTest {

    @InjectMocks
    private GroupsServiceImpl groupsService;

    @Mock
    private GroupDaoImpl groupDao;

    @Mock
    private GroupDtoMapperImpl groupDtoMapper;

    GroupDto groupDto = new GroupDto();

    Groups group = new Groups();

    @Test
    void saveGroupTest() {
        Mockito.when(groupDtoMapper.mapToGroup(groupDto)).thenReturn(group);
        groupsService.saveGroup(groupDto);
        Mockito.verify(groupDao, Mockito.times(1)).save(group);
    }

    @Test
    void updateGroupTest() {
        Mockito.when(groupDtoMapper.mapToGroup(groupDto)).thenReturn(group);

        groupsService.updateGroup(groupDto);
        Mockito.verify(groupDao, Mockito.times(1)).update(group);
    }

    @Test
    void deleteGroupTest() {
        groupsService.deleteGroup(1);
        Mockito.verify(groupDao, Mockito.times(1)).delete(1);
    }

    @Test
    void getGroupTest() {
        Mockito.when(groupDtoMapper.mapToGroupDto(group)).thenReturn(groupDto);
        Mockito.when(groupDao.get(1)).thenReturn(group);
        GroupDto actual = groupsService.getGroup(1);
        Assertions.assertEquals(groupDto, actual);

    }

    @Test
    void getAllGroupsTest() {
        List<Groups> groups = new ArrayList<>();
        Groups group1 = new Groups();
        groups.add(group);
        groups.add(group1);
        List<GroupDto> groupsDto = new ArrayList<>();
        GroupDto groupDto1 = new GroupDto();
        groupsDto.add(groupDto);
        groupsDto.add(groupDto1);
        Mockito.when(groupDao.getAll()).thenReturn(groups);
        Mockito.when(groupDtoMapper.mapToGroupDto(group)).thenReturn(groupDto);
        Mockito.when(groupDtoMapper.mapToGroupDto(group1)).thenReturn(groupDto1);
        List<GroupDto> actual = groupsService.getAllGroups();
        Assertions.assertEquals(groupsDto,actual);
    }
}