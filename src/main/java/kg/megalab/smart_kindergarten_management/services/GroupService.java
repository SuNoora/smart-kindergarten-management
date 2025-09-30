package kg.megalab.smart_kindergarten_management.services;

import kg.megalab.smart_kindergarten_management.models.dto.GroupDto;
import org.springframework.data.domain.Page;

public interface GroupService {

    GroupDto createGroup(GroupDto groupDto);

    GroupDto updateGroup(Long id, GroupDto groupDto);

    GroupDto findGroupById(Long id);

    Page<GroupDto> findAllGroups(int page, int size);

    void deleteGroup(Long id);
}