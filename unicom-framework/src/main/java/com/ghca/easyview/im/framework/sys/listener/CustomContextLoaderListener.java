package com.ghca.easyview.im.framework.sys.listener;

import com.ghca.easyview.im.framework.sys.listener.conf.ContextLoaderListenerConfigType;
import com.ghca.easyview.im.framework.sys.listener.conf.ContextLoaderListenerStatus;
import com.ghca.easyview.im.framework.util.StringUtil;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Spring启动时,按需加载Spring配置文件
 *
 * Created by Administrator on 2017/3/28.
 */
public class CustomContextLoaderListener extends ContextLoaderListener {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomContextLoaderListener.class);

    /**
     * 配置文件名称
     */
    private static final String LOADER_CONFIG_FILE = "conf-xml.properties";

    @Override
    protected void configureAndRefreshWebApplicationContext(ConfigurableWebApplicationContext wac, ServletContext sc) {
        if (ObjectUtils.identityToString(wac).equals(wac.getId())) {
            // The application context id is still set to its original default value
            // -> assign a more useful id based on available information
            String idParam = sc.getInitParameter(CONTEXT_ID_PARAM);
            if (idParam != null) {
                wac.setId(idParam);
            } else {
                // Generate default id...
                if (sc.getMajorVersion() == 2 && sc.getMinorVersion() < 5) {
                    // Servlet <= 2.4: resort to name specified in web.xml, if any.
                    wac.setId(ConfigurableWebApplicationContext.APPLICATION_CONTEXT_ID_PREFIX + ObjectUtils.getDisplayString(sc.getServletContextName()));
                } else {
                    wac.setId(ConfigurableWebApplicationContext.APPLICATION_CONTEXT_ID_PREFIX + ObjectUtils.getDisplayString(sc.getContextPath()));
                }
            }
        }

        wac.setServletContext(sc);
        String initParameter = assemblyInitParameter(sc.getInitParameter(CONFIG_LOCATION_PARAM));
        if (initParameter != null) {
            wac.setConfigLocation(initParameter);
        }
        customizeContext(sc, wac);
        wac.refresh();
    }

    /**
     * WMS对Spring启动设置开关加载配置文件
     *
     * @param webXmlConfigInitParameter web.xml配置的Spring文件
     * @return
     */
    protected String assemblyInitParameter(String webXmlConfigInitParameter) {
        String initParameter = webXmlConfigInitParameter;

        ClassPathResource resource = new ClassPathResource(LOADER_CONFIG_FILE);
        Properties configureProps;
        try {
            configureProps = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            throw new RuntimeException("加载" + LOADER_CONFIG_FILE + "配置文件失败!", e);
        }

        Object configJobDebug = configureProps.get(ContextLoaderListenerConfigType.MQ_DEBUG.getType());
        if (configJobDebug == null) {
            LOGGER.debug("当前未配置加载Spring定时任务启动参数" + ContextLoaderListenerConfigType.MQ_DEBUG.getType() + ", 系统直接启动.");
            return initParameter;
        }

        String jobDebug = String.valueOf(configJobDebug).toUpperCase();
        ContextLoaderListenerStatus listenerStatus = ContextLoaderListenerStatus.getByStatus(jobDebug);
        if (listenerStatus == null) {
            throw new RuntimeException("配置Spring定时任务启动参数" + ContextLoaderListenerConfigType.MQ_DEBUG.getType() + ", ON或者OFF, 当前配置:" + jobDebug);
        }

        String[] configures = StringUtil.split(initParameter, ",");
        if (ArrayUtils.isEmpty(configures)) {
            throw new RuntimeException("配置Spring启动文件为空!");
        }
        LOGGER.debug(LOADER_CONFIG_FILE + "配置" + ContextLoaderListenerConfigType.MQ_DEBUG.getType() + "设置为:" + jobDebug);

        // 校验配置文件是否存在
        Object jobFileName = configureProps.get(ContextLoaderListenerConfigType.MQ_CONFIG_FILE.getType());
        if (jobFileName == null) {
            throw new RuntimeException(LOADER_CONFIG_FILE + "配置" + ContextLoaderListenerConfigType.MQ_CONFIG_FILE.getType() + "设置为空!");
        }
        if (!StringUtils.contains(webXmlConfigInitParameter, String.valueOf(jobFileName))) {
            throw new RuntimeException("web.xml无" + jobFileName + "配置信息!");
        }

        if (StringUtils.equals(jobDebug, ContextLoaderListenerStatus.OFF.getStatus())) {
            LOGGER.debug(LOADER_CONFIG_FILE + "配置" + ContextLoaderListenerConfigType.MQ_DEBUG.getType() + "设置为:" + jobDebug + ", WMS不加载配置文件" + jobFileName);
            // 获取过滤之后的配置文件
            List<String> filterConfigs = Lists.newArrayList();
            for (String configure : configures) {
                if (!StringUtils.contains(configure, String.valueOf(jobFileName))) {
                    filterConfigs.add(configure);
                }
            }
            initParameter = Joiner.on(",").join(filterConfigs);
        }

        return initParameter;
    }


}