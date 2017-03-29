package com.ghca.easyview.im.framework.interfaces;

/**
 * 用户
 * Created by wangchun on 16/4/16.
 */
public interface UserInterface {
    public String getUserId();
    public String getUserName();
    public String getName();
    public String getPwd();
    public String getUserLevelIds();
    public String getUserOrgId();//一人如果归属多机构怎么处理？通过切换岗位来切换

}
