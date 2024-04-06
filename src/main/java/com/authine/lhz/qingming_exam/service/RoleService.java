package com.authine.lhz.qingming_exam.service;

import com.authine.lhz.qingming_exam.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> list();

    Role getOneById(String id);

    String create(Role role);

    boolean update(Role role);

    boolean delete(String id);
}
