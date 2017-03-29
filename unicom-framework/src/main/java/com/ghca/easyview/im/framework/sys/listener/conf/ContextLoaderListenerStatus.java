package com.ghca.easyview.im.framework.sys.listener.conf;

import org.apache.commons.lang3.StringUtils;

/**
 * 开关状态值
 *
 * Created by Administrator on 2017/3/28.
 */
public enum ContextLoaderListenerStatus {
    ON("ON"),
    OFF("OFF");
    private String status;

    ContextLoaderListenerStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static ContextLoaderListenerStatus getByStatus(String statusCode) {
        if (StringUtils.isEmpty(statusCode)) {
            return null;
        }

        for (ContextLoaderListenerStatus status : ContextLoaderListenerStatus.values()) {
            if (status.getStatus().equals(statusCode)) {
                return status;
            }
        }
        return null;
    }
}
