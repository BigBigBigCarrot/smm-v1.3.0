<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="/static/js/widget/angular.min.js"></script>
<script src="/static/js/widget/jquery-1.11.1.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>posetMan sessionTest page</title>
</head>
<body data-ng-app="myApp">
<div data-ng-controller="firsController">
<br/><br/>
sessionKey:<input type="text"  ng-model="formData.sessionKey" />
sessionValue:<input type="text"  ng-model="formData.sessionValue" />
<input type="button" value="保存session" data-ng-click="saveSession()"/>
<br/>
<input type="button" value="获取session" data-ng-click="getSession()"/>
<br/>
<label>{{result}}</label>
</div>
</body>

<script>
var myApp=angular.module("myApp",[]);
myApp.controller("firsController",function($scope){
	function init(){
		$scope.formData={};
	}
	init();
	
	$scope.saveSession=function(){
		var uri = '/PostManController/saveSession';
		$.ajax({
			url: uri,
			method: 'post',
			dataType: 'json',
			data: $scope.formData
		}).success(function(result) {
			$scope.$apply(function() {
				console.log(result);
				$scope.result=result;
			})
		})
	}
	
	$scope.getSession=function(){
		var uri = '/PostManController/getSession';
		$.ajax({
			url: uri,
			method: 'get',
			dataType: 'json',
			data: $scope.formData
		}).success(function(result) {
			$scope.$apply(function() {
				console.log(result);
				$scope.result=result;
			})
		})
	}
	
});
</script>
</html>