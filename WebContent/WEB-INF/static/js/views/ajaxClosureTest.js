/**
 * 
 */
var app=angular.module("app",[]);

app.controller("customerController",function($scope){
	
	$scope.sendAjaxRequest=function(ajaxParameter){
		sendAjaxRequest(ajaxParameter);
	}
	
	
	function sendAjaxRequest(ajaxParameter){
		var uri;
		if(ajaxParameter==1){
			uri="/TestController/ajaxClosureTestHandler";
		}else{
			uri="/Test2Controller/ajaxClosureTestHandler";
		}
		
		$.ajax({
			url:uri,
			method:'GET',
			dataType:'JSON',
			data:{
				"ajaxParameter":ajaxParameter
			}
		}).success(function(result){
			console.log("发送时的参数："+ajaxParameter+" 回传的参数:"+result);
//			if(ajaxParameter!=result){
//			}
		});
	}
});