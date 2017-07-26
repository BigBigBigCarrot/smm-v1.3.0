/**
 * 
 */
var app=angular.module("app",[]);

app.controller("customerController",function($scope){
	$scope.sendAjaxRequest=function(ajaxParameter){
		var uri="/TestController/ajaxClosureTestHandler";
		$.ajax({
			url:uri,
			method:'GET',
			dataType:'JSON',
			data:{
			}
		}).success(function(result){
			console.log(ajaxParameter);
			//console.log(result);
		});
	}
});