package com.ghca.easyview.im.framework.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义登录认证
 * Created by bingbing on 2017/3/29.
 */
@Service("loginRealm")
public class LoginRealm  extends AuthorizingRealm {

    public LoginRealm() {
        super();

    }

    /**
     * 登录认证;
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		/* 这里编写认证代码 */
//        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
//        IUserModel user =null;
//        try {
//            user= ModuleBeanUtil.getInstance().getUserService().findByUserName(token.getUsername());//baseGroupInterfacesService.findByLoginName(token.getUsername());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        if (1 == 1) {
            return new SimpleAuthenticationInfo("admin", "admin", getName());
        }
        return null;

    }

    /**
     * 权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //XXX 在这里获取到用户，并获取到相关角色和权限，在这里定义一个基础用户
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = String.valueOf(principals.getPrimaryPrincipal());//取查询获取用户名
        //获取当前用户
        //final IUserModel user =mscUsersPersonService.findByUserName(username);// userService.selectByUsername(username);
        //权限列表
//        final List<IRoleDomain> roleInfoList =CurrentUserUtil.getPermissionList();//mscAuthRoleService.findByUser(user.getUserId());
//        if(roleInfoList!=null && !roleInfoList.isEmpty()){
//            for (IRoleDomain role : roleInfoList) {
//                // 添加权限角色
//                String authRole= AuthConstant.getRole(role.getType());
//                authorizationInfo.addRole(authRole);
//                authorizationInfo.addStringPermission(authRole);//将拥有的角色权限直接添加到权限中
//                final List<IAuthDomain> mscAuthInfoList =role.getAuthList();// permissionService.selectPermissionsByRoleId(role.getId());
//                for (IAuthDomain authInfo : mscAuthInfoList) {
//                    // 添加权限
//                    authorizationInfo.addStringPermission(authInfo.getAuthKey());//(permission.getPermissionSign());
//                }
//            }
//        }
        return authorizationInfo;

    }




    public void clearCachedAuthorizationInfo(String principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(
                principal, getName());
        clearCachedAuthorizationInfo(principals);
    }

}