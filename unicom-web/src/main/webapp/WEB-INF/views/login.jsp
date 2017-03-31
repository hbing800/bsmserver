<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/jsp/global.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title><fmt:message key="mscLogin.login"/></title>
  <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
  <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
  <META HTTP-EQUIV="Expires" CONTENT="0">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
  <link rel="stylesheet" href="${rootRes}/css/bootstrap.css" type="text/css" />
  <link rel="stylesheet" href="${rootRes}/css/animate.css" type="text/css" />
  <link rel="stylesheet" href="${rootRes}/css/font-awesome.min.css" type="text/css" />
  <link rel="stylesheet" href="${rootRes}/css/simple-line-icons.css" type="text/css" />
  <link rel="stylesheet" href="${rootRes}/css/font.css" type="text/css" />
  <link rel="stylesheet" href="${rootRes}/css/app.css" type="text/css" />
  <link rel="stylesheet" href="${rootRes}/css/login.css" type="text/css" />

  <script src="${rootRes}/js/libs/angularjs/angular1.4.0/angular.js"></script>
  <script src="${rootRes}/js/libs/angularjs/angular1.4.0/angular-ui-router.min.js"></script>
  <script src="${rootRes}/js/libs/seajs/sea.js"></script>
  <script src="${rootPath}/resource/core/login/mscLoginCtrl.js" type="text/javascript" ></script>
</head>
<body>
<div ng-app="loginApp" style="overflow: hidden;">
  <div class="container w-xxl w-auto-xs" ng-controller="loginFormController">
    <div class="m-b-lg">
      <form name="form" class="form-horizontal">
        <div class="mycenter">
          <div class="mysign">
            <div class="col-lg-11 text-center text-info">
              <h2><fmt:message key="mscLogin.login"/></h2>
            </div>
            <div class="col-lg-11 text-danger wrapper text-center" ng-show="authError">
              {{errorInfo}}
            </div>
            <div class="col-lg-10">
              <input type="text" class="form-control" ng-model="loginForm.userName" placeholder="<fmt:message key="mscLogin.userName.please"/>" required autofocus/>
            </div>
            <div class="col-lg-10"></div>
            <div class="col-lg-10">
              <input type="password" class="form-control" ng-model="loginForm.pwd" placeholder="<fmt:message key="mscLogin.pwd.please"/>" required autofocus/>
            </div>
            <div class="col-lg-10"></div>
            <div class="col-lg-10">
              <button type="button" class="btn btn-success col-lg-12" ng-click="login()"><fmt:message key="mscLogin.login"/></button>
            </div>
          </div>
        </div>

      </form>
    </div>
  </div>
</div>

<script>
  var G = {};
  G.path ={
    RootPath:'${rootPath}',//根目录
    serverRootPath:"",//服务请求根路径
    ResPath:"${rootRes}",//资源文件根路径
    TemplatePath:"${rootTemplate}",//模板路径
    modulePath:"",//模块模板路径 由模块设定
    moduleResPath:"",//模块资源位置
    moduleTemplatePath:""//模块资源位置
  };
</script>
</body>
</html>

