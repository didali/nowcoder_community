package com.dida.nowcoder.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis() {
/*        String redisKey = "test:count";

        redisTemplate.opsForValue().set(redisKey, 1);

        System.out.println(redisTemplate.opsForValue().get(redisKey));
        System.out.println(redisTemplate.opsForValue().increment(redisKey));
        System.out.println(redisTemplate.opsForValue().decrement(redisKey));*/

        /*String redidKey = "test:user";

        redisTemplate.opsForHash().put(redidKey, "id", 1);
        redisTemplate.opsForHash().put(redidKey, "name", "zhangsan");

        System.out.println(redisTemplate.opsForHash().get(redidKey, "id"));
        System.out.println(redisTemplate.opsForHash().get(redidKey, "name"));*/

        /*//删除key
        redisTemplate.delete("test:count");
        redisTemplate.delete("test:user");
    */

        //查看某个key是否存在
        System.out.println(redisTemplate.hasKey("test:count"));
        System.out.println(redisTemplate.hasKey("test:user"));

        //针对某个key设置过期时间
        //redisTemplate.expire("test:user", 10, TimeUnit.SECONDS);

    }

}
