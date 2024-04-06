package com.authine.lhz.qingming_exam.service.impl;

import com.authine.lhz.qingming_exam.dao.RoleDao;
import com.authine.lhz.qingming_exam.entity.Role;
import com.authine.lhz.qingming_exam.dto.RoleDto;
import com.authine.lhz.qingming_exam.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Resource
    RoleDao dao;

    @Override
    public List<RoleDto> list() {
        return dao.findAll().stream().map(RoleDto::fromRole).collect(Collectors.toList());
    }

    @Override
    public RoleDto getOneById(String id) {
        Optional<Role> result = dao.findById(id);
        if (result.orElse(null) != null) {
            return RoleDto.fromRole(result.orElse(null));
        }
        return null;
    }

    @Override
    public String create(Role role) {
        return dao.save(role).getId();
    }

    @Override
    public boolean update(Role role) {
        dao.save(role);
        return true;
    }

    @Override
    public boolean delete(String id) {
        dao.deleteById(id);
        return true;
    }

    @Override
    public boolean createList(List<Role> roles) {
        dao.saveAll(roles);
        return true;
    }

    @Override
    public boolean updateList(List<Role> roles) {
        dao.saveAll(roles);
        return true;
    }

    @Override
    public boolean deleteList(List<String> ids) {
        dao.deleteAllById(ids);
        return true;
    }
}
