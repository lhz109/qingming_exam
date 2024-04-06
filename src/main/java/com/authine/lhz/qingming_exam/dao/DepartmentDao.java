package com.authine.lhz.qingming_exam.dao;

import com.authine.lhz.qingming_exam.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentDao extends JpaRepository<Department, String> {
}
