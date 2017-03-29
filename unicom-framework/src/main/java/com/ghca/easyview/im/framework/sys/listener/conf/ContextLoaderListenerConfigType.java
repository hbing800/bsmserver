package com.ghca.easyview.im.framework.sys.listener.conf;

/**
 * 启动过滤配置文件类型
 *
 * Created by Administrator on 2017/3/28.
 */
public enum ContextLoaderListenerConfigType {

    MQ_DEBUG("MQ.DEBUG"),MQ_CONFIG_FILE("MQ.CONFIG.FILE");

    private String type;

    public String getType() {
        return type;
    }

    ContextLoaderListenerConfigType(String type) {
        this.type = type;
    }

}
