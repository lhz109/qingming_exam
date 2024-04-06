package com.authine.lhz.qingming_exam.service;
import com.authine.lhz.qingming_exam.entity.Role;
import com.authine.lhz.qingming_exam.dto.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> list();

    RoleDto getOneById(String id);

    String create(Role role);

    boolean update(Role role);

    boolean delete(String id);

    boolean createList(List<Role> roles);

    boolean updateList(List<Role> roles);

    boolean deleteList(List<String> ids);
}
