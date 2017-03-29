package com.ghca.easyview.im.framework.entity;

import com.ghca.easyview.im.framework.persistence.DataEntity;

/**
 * 用户模型
 *
 * Created by Administrator on 2017/3/28.
 */
public class User extends DataEntity<User> {

    protected String userId;
    protected String userName;
    protected String loginName;
    protected String pwd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
