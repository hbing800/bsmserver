package com.ghca.easyview.im.framework.common;

import com.ghca.easyview.im.framework.loader.PropertiesLoader;
import com.ghca.easyview.im.framework.util.SpringContextHolderUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

/**
 * 全局配置类
 *
 * Created by bingbing on 2017/3/27.
 */
public class Global {

    private static Logger logger = LoggerFactory.getLogger(Global.class);

    private  static PropertiesLoader propertyConfigurer;

    private static PropertiesLoader getPropertyConfigurer(){
        if(propertyConfigurer == null){
            propertyConfigurer = SpringContextHolderUtil.getBean("propertyConfigurer");
        }
        return propertyConfigurer;
    }



    /**
     * 实例对象
     */
//    private static Global global = new Global();


    /**
     * 保存全局属性值
     */
    private static Map<String, String> map = Maps.newHashMap();


//    /**
//     * 属性文件加载对象，确保项目目录下存在此文件config.properties
//     */
//    private static PropertiesLoader loader = new PropertiesLoader();


    /**
     * 当前登录用户
     */
    public static final String SESSION_LOGIN_USER = "SESSION_LOGIN_USER";

    /**
     * 当前登录用户名
     */
    public static final String LOGIN_NAME="loginname";

    /**
     * 当前登录密码
     */
    public static final String LOGIN_PASS="loginpwd";

    /**
     * 当前登录用户ID
     */
    public static final String LOGIN_ID="id";
    /**
     * 当前用户登录数据角色信息
     */
    public static final String USER_DATA_ROLE_IDS="USER_DATA_ROLE_IDS";


    /**
     * 静态常量 SHOW={@value}
     */
    public static final String SHOW = "1";


    /**
     * 静态常量 HIDE={@value}
     */
    public static final String HIDE = "0";

    /**
     * 静态常量 YES={@value}
     */
    public static final String YES = "1";

    /**
     * 静态常量 NO={@value}
     */
    public static final String NO = "0";

    /**
     * 静态常量 (对) TRUE={@value}
     */
    public static final String TRUE = "true";

    /**
     * 静态常量 (错)FALSE={@value}
     */
    public static final String FALSE = "false";



    /**
     * 属性类型：字符串
     */
    public final static Integer ATTR_TYPE_STRING = 1;

    /**
     * 属性类型：日期
     */
    public final static Integer ATTR_TYPE_DATE = 2;

    /**
     * 属性类型：整形
     */
    public final static Integer ATTR_TYPE_INTEGER = 3;

    /**
     * 输入属性类型：整形
     */
    public final static Integer ATTR_INPUT_TYPE_INTEGER = 9;

    /**
     * 属性类型：浮点型
     */
    public final static Integer ATTR_TYPE_DOUBLE = 4;


    /**
     * 获取当前类对象信息
     *
     * @return {@value}
     */
//    public static Global getInstance() {
//        return global;
//    }


    /**
     * 根据key信息获取对应的value
     *
     * @param key 需要获取的key
     * @return 配置文件key对应的value
     */
    public static String getProperties(String key) {
        logger.error("Properties  Key  = 【" + key + "】");
        String value = map.get(key);//相当于缓存
        if (value == null) {
            value = (String) getPropertyConfigurer().getProperty(key);
            if (StringUtils.isNotBlank(value)) {
                map.put(key, value);
            }
        }
        logger.error("Properties value = 【" + value + "】");
        return value;
    }


}
