package com.authine.lhz.qingming_exam.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class SnowFlakeUtil {

    private static final Snowflake snowflake = IdUtil.createSnowflake(1, 1);

    public static String generatorId() {
        return snowflake.nextIdStr();
    }
}
