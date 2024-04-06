package com.authine.lhz.qingming_exam.service.impl;

import com.authine.lhz.qingming_exam.dao.RoleDao;
import com.authine.lhz.qingming_exam.entity.Role;
import com.authine.lhz.qingming_exam.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    RoleDao dao;

    @Override
    @Transactional
    public List<Role> list() {
        return dao.findAll();
    }

    @Override
    @Transactional
    public Role getOneById(String id) {
        Optional<Role> result = dao.findById(id);
        return result.orElse(null);
    }

    @Override
    @Transactional
    public String create(Role role) {
        return dao.save(role).getId();
    }

    @Override
    @Transactional
    public boolean update(Role role) {
        dao.save(role);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        dao.deleteById(id);
        return true;
    }
}
