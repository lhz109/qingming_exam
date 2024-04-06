package com.authine.lhz.qingming_exam.config;

import com.authine.lhz.qingming_exam.util.SnowFlakeUtil;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class SnowIdGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return SnowFlakeUtil.generatorId();
    }
}
