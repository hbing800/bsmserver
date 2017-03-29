<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.ghca.easyview.im.framework.util.CurrentUserUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
  //获取语言配置
  Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
  String lang = locale.getLanguage();

  //获取当前用户信息
//  IUserModel user=CurrentUserUtil.getUser();
//  if(user!=null){
//    request.setAttribute("userName",user.getName());
//  }

  request.setAttribute("userName", CurrentUserUtil.getUserName());
%>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<c:set var="ctxPlugin" value="${pageContext.request.contextPath}/static/plugin"/>
<c:set var="lang" value="<%=lang%>"/>
