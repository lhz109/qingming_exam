package com.authine.lhz.qingming_exam.dao;

import com.authine.lhz.qingming_exam.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, String> {
}
