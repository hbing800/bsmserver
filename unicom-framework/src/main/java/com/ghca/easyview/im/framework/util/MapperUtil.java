package com.ghca.easyview.im.framework.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;


/**
 * Description 获取mybatis的sqlSessionFactory
 *
 * Created by Administrator on 2017/3/29.
 */
public class MapperUtil {


    private final static SqlSessionFactory sqlSessionFactory;

    //静态初始化块
    static {
        String resource = "mybatis-config.xml";//确认此文件是否存在
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    //定义静态方法获取
    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
