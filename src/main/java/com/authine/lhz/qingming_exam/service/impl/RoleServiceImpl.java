package com.authine.lhz.qingming_exam.service.impl;

import com.authine.lhz.qingming_exam.dao.RoleDao;
import com.authine.lhz.qingming_exam.entity.Role;
import com.authine.lhz.qingming_exam.dto.RoleDto;
import com.authine.lhz.qingming_exam.entity.Worker;
import com.authine.lhz.qingming_exam.service.RoleService;
import com.authine.lhz.qingming_exam.util.RedisUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Resource
    RoleDao dao;

    @Resource
    RedisUtil redisUtil;

    @Override
    public List<RoleDto> list() {
        List<RoleDto> roles = redisUtil.getList("roles", RoleDto.class);
        if (roles == null) {
            //悲观锁，只需要让一个线程来重建缓存
            synchronized ("roleList") {
                //再次判断
                roles = redisUtil.getList("roles", RoleDto.class);
                if (roles == null) {
                    roles = dao.findAll().stream().map(RoleDto::fromRole).collect(Collectors.toList());
                    redisUtil.setList("roles", roles);
                }
            }
        }
        return roles;
    }

    @Override
    public RoleDto getOneById(String id) {
        //判断空、逻辑空
        String result = redisUtil.getIdWithObject(id, String.class);
        //重建缓存
        if (result == null) {
            synchronized (id.intern()) {
                //再次判断
                result = redisUtil.getIdWithObject(id, String.class);
                if (result == null) {
                    Optional<Role> selected = dao.findById(id);
                    //数据库是否存在，不存在，直接缓存逻辑空值
                    if (selected.isPresent()) {
                        redisUtil.setIdWithObject(id, RoleDto.fromRole(selected.get()));
                        result = redisUtil.getIdWithObject(id, String.class);
                    } else {
                        redisUtil.setIdWithObject(id, "null");
                        result = "null";
                    }
                }
            }
        }
        //判断是否为逻辑空值
        if (Objects.equals(result, "null")) {
            return null;
        }
        return redisUtil.getIdWithObject(id, RoleDto.class);
    }

    @Override
    public String create(Role role) {
        String id = dao.save(role).getId();
        deleteCacheForId(id, role.getWorkers().stream().map(Worker::getId).collect(Collectors.toList()));
        return id;
    }

    @Override
    public boolean update(Role role) {
        RoleDto before = getOneById(role.getId());
        dao.save(role);
        before.getWorkers().addAll(role.getWorkers().stream().map(Worker::getId).collect(Collectors.toList()));
        deleteCacheForId(role.getId(), before.getWorkers());
        return true;
    }

    @Override
    public boolean delete(String id) {
        RoleDto before = getOneById(id);
        dao.deleteById(id);
        deleteCacheForId(id, before.getWorkers());
        return true;
    }

    @Override
    public boolean createList(List<Role> roles) {
        List<Role> selected = dao.saveAll(roles);
        List<String> ids = selected.stream().map(Role::getId).collect(Collectors.toList());
        ids.addAll(selected.stream().flatMap((e) -> e.getWorkers().stream().map(Worker::getId)).collect(Collectors.toList()));
        deleteCacheForIds(ids);
        return true;
    }

    @Override
    public boolean updateList(List<Role> roles) {
        List<String> ids = roles.stream().map(Role::getId).collect(Collectors.toList());
        ids.addAll(roles.stream().flatMap((e) -> getOneById(e.getId()).getWorkers().stream()).collect(Collectors.toList()));
        dao.saveAll(roles);
        ids.addAll(roles.stream().flatMap((e) -> e.getWorkers().stream().map(Worker::getId)).collect(Collectors.toList()));
        deleteCacheForIds(ids);
        return true;
    }

    @Override
    public boolean deleteList(List<String> ids) {
        List<String> strings = ids.stream().flatMap((e) -> getOneById(e).getWorkers().stream()).collect(Collectors.toList());
        dao.deleteAllById(ids);
        ids.addAll(strings);
        deleteCacheForIds(ids);
        return true;
    }

    @Async
    protected void deleteCacheForId(String id, List<String> ids) {
        redisUtil.deleteList("roles");
        redisUtil.deleteList("workers");
        redisUtil.deleteIdWithObject(id);
        ids.forEach((e) -> redisUtil.deleteIdWithObject(e));
    }

    @Async
    protected void deleteCacheForIds(List<String> ids) {
        redisUtil.deleteList("roles");
        redisUtil.deleteList("workers");
        ids.forEach((e) -> redisUtil.deleteIdWithObject(e));
    }
}
