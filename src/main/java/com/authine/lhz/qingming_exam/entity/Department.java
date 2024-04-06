package com.authine.lhz.qingming_exam.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "department")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Department {
    @Id
    @GenericGenerator(name = "snow", strategy = "com.authine.lhz.qingming_exam.config.SnowIdGenerator")
    @GeneratedValue(generator = "snow")
    private String id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "departments")
    private List<Worker> workers;
}
