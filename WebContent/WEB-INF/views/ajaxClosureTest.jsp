<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ajax回调，javaScript闭包测试</title>
<script src="/static/js/widget/angular.min.js"></script>
<script src="/static/js/widget/jquery-1.11.1.min.js"></script>
<script src="/static/js/views/ajaxClosureTest.js"></script>
</head>
<body ng-controller="customerController">
<h1>
发送两次ajax请求，次序为r1(参数p1)、r2(参数p2)，其中r2回调success方法先于r1执行，<br/>
r1与r2在回调中各自调用的参数p1、p2并未混乱
</h1>
<!--  
ajaxParameter:<input type="number" ng-model="ajaxParameter"/>
<br/>
<input type="button" value="sendAjaxRequest" ng-click="sendAjaxRequest(ajaxParameter)"/>
-->
<input type="button" value="sendAjaxRequest(1)" ng-click="sendAjaxRequest(1)"/>
<input type="button" value="sendAjaxRequest(2)" ng-click="sendAjaxRequest(2)"/>
</body>

</html>