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
ajaxParameter:<input type="text" ng-model="ajaxParameter"/>
<br/>
<input type="button" value="sendAjaxRequest" ng-click="sendAjaxRequest(ajaxParameter)"/>
</body>

</html>