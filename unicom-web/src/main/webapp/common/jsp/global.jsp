<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  //全局路径文件
  String rootPath=request.getContextPath();
  String rootRes=rootPath+"/common";

  request.setAttribute("rootPath",rootPath);//项目根路径
//  request.setAttribute("rootRes",rootPath+"/common");//资源文件路径 默认取本地的，也可以取CDN中的
  request.setAttribute("rootRes",rootRes);//资源文件路径 默认取本地的，也可以取CDN中的
  request.setAttribute("rootTemplate",rootPath+"/common/template");
  //获取当前用户信息
  request.setAttribute("userName","admin");
%>
