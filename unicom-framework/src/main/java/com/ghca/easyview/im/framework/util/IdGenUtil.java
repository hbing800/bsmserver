package com.ghca.easyview.im.framework.util;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类
 *
 * Created by Administrator on 2017/3/28.
 */
@Service
@Lazy(false)
public class IdGenUtil implements SessionIdGenerator {
    private static SecureRandom random = new SecureRandom();


    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     *
     * @return 中间无-分割
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * 使用SecureRandom随机生成Long.
     *
     * @return
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }


    /**
     * 基于Base62编码的SecureRandom随机生成bytes.
     *
     * @param length 需要生成的长度
     * @return
     */
    public static String randomBase62(int length) {
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return EncodesUtil.encodeBase62(randomBytes);
    }

    @Override
    public Serializable generateId(Session session) {
        return IdGenUtil.uuid();

    }


}
