//package com.ghca.easyview.im.framework.util;
//
//import com.ghca.easyview.im.core.dao.*;
//import com.ghca.easyview.im.core.entity.*;
//import com.ghca.easyview.im.core.utils.security.shiro.session.Principal;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.UnavailableSecurityManagerException;
//import org.apache.shiro.session.InvalidSessionException;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.Subject;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 用户工具类
// *
// * Created by Administrator on 2017/3/29.
// */
//public class UserUtil {
//
//    private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
//    private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
//    private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
//    private static OssCenterDao ossCenterDao = SpringContextHolder.getBean(OssCenterDao.class);
//
//    public static final String USER_CACHE = "userCache";
//    public static final String USER_CACHE_ID_ = "id_";
//    public static final String USER_CACHE_LOGIN_NAME_ = "ln";
//
//
//    public static final String CACHE_ROLE_LIST = "roleList";
//    public static final String CACHE_MENU_LIST = "menuList";
//    public static final String CACHE_OSSCENTER_LIST = "osscenterList";
//    public static final String CACHE_ACCESS_LIST = "accessList";
//
//
//    /**
//     * 根据ID获取用户
//     *
//     * @param id
//     * @return 取不到返回null
//     */
//    public static User get(String id) {
//        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
//        if (user == null) {
//            user = userDao.get(id);
//            if (user == null) {
//                return null;
//            }
//            user.setRoleList(getRoleListByUser(user));
//            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
//            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
//        }
//        return user;
//    }
//
//    /**
//     * 根据登录名获取用户
//     *
//     * @param loginName
//     * @return 取不到返回null
//     */
//    public static User getByLoginName(String loginName) {
//        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
//        user = null;
//        if (user == null) {
//            user = userDao.getByLoginName(new User(null, loginName));
//            if (user == null) {
//                return null;
//            }
//
//
//            user.setRoleList(getRoleListByUser(user));
//            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
//            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
//        }
//
//        return user;
//    }
//
//
//    /**
//     * 根据用户获取用户权限信息
//     *
//     * @param user
//     * @return
//     */
//    private static List<Role> getRoleListByUser(User user) {
//        List<Role> roleList;
//        Role role = new Role();
//        role.setUseable("1");
//        if (user.isAdmin()) {
//            roleList = roleDao.findAllList(role);
//        } else {
//            role.setUser(user);
//            roleList = roleDao.findList(role);
//        }
//        return roleList;
//    }
//
//    /**
//     * 清除当前用户缓存
//     */
//    public static void clearCache() {
//        removeCache(CACHE_ROLE_LIST);
//        removeCache(CACHE_MENU_LIST);
//        removeCache(CACHE_OSSCENTER_LIST);
//        removeCache(CACHE_ACCESS_LIST);
//        UserUtils.clearCache(getUser());
//    }
//
//    /**
//     * 清除指定用户缓存
//     *
//     * @param user
//     */
//    public static void clearCache(User user) {
//        CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
//        CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
//    }
//
//    /**
//     * 获取当前用户
//     *
//     * @return 取不到返回 new User()
//     */
//    public static User getUser() {
//        Principal principal = getPrincipal();
//        if (principal != null) {
//            User user = get(principal.getId());
//            if (user != null) {
//                return user;
//            }
//            return new User();
//        }
//        // 如果没有登录，则返回实例化空的User对象。
//        return new User();
//    }
//
//    /**
//     * 获取当前用户角色列表
//     *
//     * @return
//     */
//    public static List<Role> getRoleList() {
//        @SuppressWarnings("unchecked")
//        List<Role> roleList = (List<Role>) getCache(CACHE_ROLE_LIST);
//        if (roleList == null) {
//            User user = getUser();
//            Role role = new Role();
//            role.setUseable("1");
//            if (user.isAdmin()) {
//                roleList = roleDao.findAllList(role);
//            } else {
//                role.setUser(getUser());
//                roleList = roleDao.findList(role);
//            }
//            putCache(CACHE_ROLE_LIST, roleList);
//        }
//        return roleList;
//    }
//
//
//    /**
//     * 获取当前用户授权菜单
//     *
//     * @return
//     */
//    public static List<Menu> getMenuList() {
//        @SuppressWarnings("unchecked")
//        List<Menu> menuList = (List<Menu>) getCache(CACHE_MENU_LIST);
//        if (menuList == null) {
//            User user = getUser();
//
//
//            if (user.isAdmin()) {
//                menuList = menuDao.findAllMenus(new Menu());
//            } else {
//                Menu m = new Menu();
//                m.setUserId(user.getId());
//                menuList = menuDao.findByUserId(m);
//            }
//            //组装树形结构
//
//
//            Map<String, Menu> treeMap = new LinkedHashMap<>();
//            for (Menu menu : menuList) {
//                treeMap.put(menu.getId(), menu);
//            }
//
//
//            for (int i = 0; i < menuList.size(); i++) {
//                Menu tempTree = menuList.get(i);
//                Menu mapTree = null;
//
//                if (tempTree.getParentMenu() != null && StringUtils.isNotBlank(tempTree.getParentMenu().getId())) {
//                    mapTree = treeMap.get(tempTree.getParentMenu().getId());
//                }
//
//
//                if (null != mapTree && null != tempTree.getParentMenu() && !tempTree.getId().equals(tempTree.getParentMenu().getId())) {
//                    if (mapTree.getChildMenu() == null) {
//                        List<Menu> childMenu = new ArrayList<>();
//                        mapTree.setChildMenu(childMenu);
//                    }
//                    mapTree.getChildMenu().add(tempTree);
//                }
//            }
//
//
//            putCache(CACHE_MENU_LIST, menuList);
//        }
//        return menuList;
//    }
//
//
//    /**
//     * 获取当前用户的运维中心信息
//     *
//     * @return
//     */
//    public static List<OssCenter> getOsscenterList() {
//
//        List<OssCenter> ossCenterList = (List<OssCenter>) getCache(CACHE_OSSCENTER_LIST);
//        if (ossCenterList == null) {
//            User user = getUser();
//            if (user.isAdmin()) {
//                ossCenterList = ossCenterDao.findAllList(new OssCenter());
//            } else {
//                OssCenter ossCenter = new OssCenter(user);
//                ossCenterList = ossCenterDao.findList(ossCenter);
//            }
//            putCache(CACHE_OSSCENTER_LIST, ossCenterList);
//        }
//        return ossCenterList;
//
//
//    }
//
//
//
//    /**
//     * 获取授权主要对象
//     */
//    public static Subject getSubject() {
//        return SecurityUtils.getSubject();
//    }
//
//    /**
//     * 获取当前登录者对象
//     */
//    public static Principal getPrincipal() {
//        try {
//            Subject subject = SecurityUtils.getSubject();
//            Principal principal = (Principal) subject.getPrincipal();
//            if (principal != null) {
//                return principal;
//            }
////			subject.logout();
//        } catch (UnavailableSecurityManagerException e) {
//
//        } catch (InvalidSessionException e) {
//
//        }
//        return null;
//    }
//
//    public static Session getSession() {
//        return Principal.getSession();
//
////        try {
////            Subject subject = SecurityUtils.getSubject();
////            Session session = subject.getSession(false);
////            if (session == null) {
////                session = subject.getSession();
////            }
////            if (session != null) {
////                return session;
////            }
//////			subject.logout();
////        } catch (InvalidSessionException e) {
////
////        }
////        return null;
//    }
//
//    // ============== User Cache ==============
//
//    public static Object getCache(String key) {
//        return getCache(key, null);
//    }
//
//    public static Object getCache(String key, Object defaultValue) {
////		Object obj = getCacheMap().get(key);
//        Object obj = getSession().getAttribute(key);
//        return obj == null ? defaultValue : obj;
//    }
//
//    public static void putCache(String key, Object value) {
////		getCacheMap().put(key, value);
//        getSession().setAttribute(key, value);
//    }
//
//    public static void removeCache(String key) {
////		getCacheMap().remove(key);
//        getSession().removeAttribute(key);
//    }
//
////	public static Map<String, Object> getCacheMap(){
////		Principal principal = getPrincipal();
////		if(principal!=null){
////			return principal.getCacheMap();
////		}
////		return new HashMap<String, Object>();
////	}
//
//
//    public static User getCurrentUser() {
//        Subject subject = SecurityUtils.getSubject();
//        Principal principal = (Principal) subject.getPrincipal();
//        User user = new User();
//        if (null != principal) {
//            user.setId(principal.getId());
//            user.setLoginName(principal.getLoginName());
//            user.setName(principal.getName());
//
//        }
//
//        return user;
//    }
//
//
//    /**
//     * 获取当前用户权限信息，排除超级管理员，主要用于查询根据用户角色过滤,如果对于的sql逻辑是根据当前条件过滤可以使用，其它请勿使用此方法
//     *
//     * @return
//     */
//    public static User getCurrentUserAndRole() {
//        Subject subject = SecurityUtils.getSubject();
//        Principal principal = (Principal) subject.getPrincipal();
//        User user = new User();
//        if (null != principal) {
//            user.setId(principal.getId());
//            if (!user.isAdmin()) {
//                user.setLoginName(principal.getLoginName());
//                user.setName(principal.getName());
//                user.setRoleList(getRoleListByUser(user));//用户权限
//            } else {
//                user.setId(null);
//
//            }
//
//        }
//
//        return user;
//    }
//
//}
