package com.ghca.easyview.im.framework.loader;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * 文件载入工具类. 可载入多个properties文件,
 * 相同的属性在最后载入的文件中的值将会覆盖之前的值，但以System的Property优先.
 *
 * Created by bingbing on 2017/3/27.
 */
public class PropertiesLoader extends PropertyPlaceholderConfigurer {

    private static Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);
    private Properties properties=null;
    protected void processProperties( ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        // cache the properties
        properties=props;
        super.processProperties(beanFactoryToProcess, props);
    }

    /**
     * 获取整个配置
     * @return
     */
    public Properties getProperties() {
        return properties;
    }
    /**
     * 根据key获取属性
     * @param key
     * @return
     */
    public Object getProperty(String key){
        return properties.get(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public Object setProperty(String key, String value) {
        return properties.setProperty(key, value);
    }

}
