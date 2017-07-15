<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="/static/js/widget/angular.min.js"></script>
<script src="/static/js/widget/jquery-1.11.1.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>download page</title>
</head>
<body data-ng-app="myApp">
<div data-ng-controller="firsController">
<a href="../../download/商户结算清单导出.xls"">商户结算清单导出.xls</a>
<br/><br/>
<input type="button" value="生成并下载excel" data-ng-click="exportExcel()"/>
<input type="button" value="生成并下载excel-合并单元格" data-ng-click="exportExcelCellCombined()"/>
<input type="button" value="生成并下载excel-导出报价明细查询" data-ng-click="exportQuoteDetails()"/>
</div>
</body>

<script>
var myApp=angular.module("myApp",[]);
myApp.controller("firsController",function($scope){
	$scope.exportExcel=function(){
		window.location.href = '/DownloadController/exportExcel';
	}
	
	$scope.exportExcelCellCombined=function(){
		window.location.href = '/DownloadController/exportExcelCellCombined';
	}
	
	$scope.exportQuoteDetails=function(){
		window.location.href = '/DownloadController/exportQuoteDetails';
	}
});
</script>
</html>