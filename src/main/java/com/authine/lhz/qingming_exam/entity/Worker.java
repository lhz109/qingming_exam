package com.authine.lhz.qingming_exam.entity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

//多对多CRUD策略：
//新增：新增数据，新增表关系（根据id）
//修改：先删除再新增（根据id）
//删除：先删除数据，再删除表关系
@Data
@Entity
@Table(name = "worker")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Worker {
    @Id
    @GenericGenerator(name = "snow", strategy = "com.authine.lhz.qingming_exam.config.SnowIdGenerator")
    @GeneratedValue(generator = "snow")
    private String id;

    @Column
    private String name;

    @ManyToMany
    @JoinTable(name = "worker_role", joinColumns = @JoinColumn(name = "worker_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @ManyToMany
    @JoinTable(name = "department_worker", joinColumns = @JoinColumn(name = "worker_id"), inverseJoinColumns = @JoinColumn(name = "department_id"))
    private List<Department> departments;
}
