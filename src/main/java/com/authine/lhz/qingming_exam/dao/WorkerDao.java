package com.authine.lhz.qingming_exam.dao;

import com.authine.lhz.qingming_exam.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerDao extends JpaRepository<Worker, String> {
}
