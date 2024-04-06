package com.authine.lhz.qingming_exam.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RedisUtil {
    @Resource
    StringRedisTemplate stringRedisTemplate;

    public <R> void setList(String key, List<R> list) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(list));
    }

    public <R> List<R> getList(String key, Class<R> type) {
        String result = stringRedisTemplate.opsForValue().get(key);
        if (StrUtil.isBlank(result)) {
            return null;
        }
        return JSONUtil.toList(result, type);
    }

    public void deleteList(String key) {
        stringRedisTemplate.delete(key);
    }

    public void setIdWithObject(String id, Object object) {
        stringRedisTemplate.opsForValue().set(id, JSONUtil.toJsonStr(object));
    }

    public <R> R getIdWithObject(String id, Class<R> type) {
        String result = stringRedisTemplate.opsForValue().get(id);
        if (StrUtil.isBlank(result)) {
            return null;
        }
        return JSONUtil.toBean(result, type);
    }

    public void deleteIdWithObject(String id) {
        stringRedisTemplate.delete(id);
    }
}
