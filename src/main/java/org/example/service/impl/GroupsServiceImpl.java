package org.example.service.impl;

import org.example.dao.GroupDao;
import org.example.dao.impl.GroupDaoImpl;
import org.example.dto.GroupDto;
import org.example.dto.mapper.GroupDtoMapper;
import org.example.entity.Groups;
import org.example.service.GroupService;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

public class GroupsServiceImpl implements GroupService {
    private String dbProp = "db.properties";
    private GroupDao groupDao = new GroupDaoImpl(dbProp);
    private GroupDtoMapper groupDtoMapper = Mappers.getMapper(GroupDtoMapper.class);


    @Override
    public void saveGroup(GroupDto groupDto) {
        groupDao.save(map(groupDto));
    }

    @Override
    public void updateGroup(GroupDto groupDto) {
        groupDao.update(map(groupDto));

    }

    @Override
    public void deleteGroup(long id) {
        groupDao.delete(id);

    }

    @Override
    public GroupDto getGroup(long id) {
        return  groupDtoMapper.mapToGroupDto(groupDao.get(id));
    }

    @Override
    public List<GroupDto> getAllGroups() {
        List<Groups> groups = groupDao.getAll();
        return groups.stream()
                .map(group -> groupDtoMapper.mapToGroupDto(group))
                .collect(Collectors.toList());
    }

    private Groups map(GroupDto groupDto) {
        return groupDtoMapper.mapToGroup(groupDto);
    }
}
