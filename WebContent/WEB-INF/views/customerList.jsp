<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>customerList</title>
<script src="/static/js/widget/angular.min.js"></script>
<script src="/static/js/widget/jquery-1.11.1.min.js"></script>
</head>
<body ng-controller="customerController">
<P><h1>customerList</h1></P>
<input type="button" value="sendCustomerData(apart)" onClick="" ng-click="sendCustomerData()">&nbsp;
<input type="button" value="sendCustomerData(json obejct)" onClick="" ng-click="sendCustomerData2()">&nbsp;
<input type="button" value="sendJsonStr" onClick="" ng-click="sendJsonStr()">&nbsp;
<input type="button" value="getData" onClick="" ng-click="getData()">
<br/><br/>

<div >
<input type="text" ng-model="formData.firstName"/>
name:	{{formData.firstName}}<br/>
<input type="text" ng-model="formData.lastName"/>
gender:	{{formData.lastName}}<br/>
<input type="button" value="getCustomerEntity" ng-click="getCustomerEntity()"><br/>
ajaxResult.data:<label>{{ajaxResultData}}</label>
<br/><br/>
ajaxResult:<label>{{ajaxResult}}</label>
</div>
<br/>

<p>
<table>
	<thead>
     <tr>
       <th>id</th>
       <th>firstName</th>
       <th>lastName</th>
       <th>dob</th>
       <th>phone</th>
     </tr>
   </thead>
   <tbody>
          <tr ng-repeat="item in customerList">
            <td>{{item.id}}</td>
            <td>{{item.firstName}}</td>
            <td>{{item.lastName}}</td>
            <td>{{item.dob}}</td>
            <td>{{item.phone}}</td>
          </tr>
        </tbody>
</table>
<br/>
<label>pageSize</label><input type="number" min="1" step="1" ng-model="pageSize"/>
<label>currPage</label><input type="number" min="1" step="1" ng-model="currPage"/>
<input type="button" value="getCustomerList()" ng-click="getCustomerList()"><br/>
</p>
<script>
var app=angular.module("app",[]);

app.controller("customerController",function($scope){
	
	function init(){
		$scope.formData={};
		
		$scope.formData.firstName="Brian";
		$scope.formData.lastName="Cox";
		$scope.formData.id="86";
		$scope.formData.phone="111";
		$scope.formData.extraPram="extraPram";
		
		$scope.ajaxResult={};
		
		$scope.pageSize=10;
		$scope.currPage=1;
	}
	init();
	
	$scope.sendCustomerData=function(){
		var uri = '/CustomerController/receiveCustomer';
		$.ajax({
			url: uri,
			method: 'post',
			dataType: 'json',
			data: $scope.formData
		}).success(function(result) {
			$scope.$apply(function() {
				$scope.ajaxResultData = result.data;
				$scope.ajaxResult = result;
			})
		})
	}

	$scope.sendCustomerData2=function(){
		var uri = '/CustomerController/receiveCustomer';
		var customer={"id":1,"firstName":"Martin","lastName":"Jacques","dob":'',"phone":"123"};
		$scope.formData.customer=customer;
		$.ajax({
			url: uri,
			method: 'post',
			dataType: 'json',
			data: $scope.formData.customer
		}).success(function(result) {
			$scope.$apply(function() {
				$scope.ajaxResultData = result.data;
				$scope.ajaxResult = result;
			})
		})
	}
	
	$scope.sendJsonStr=function(){
		var customerList=[{"id":1,"firstName":"Martin","lastName":"Jacques","dob":'',"phone":"123"},
		                  {"id":2,"firstName":"Cynthia","lastName":"Green","dob":'1968-02-05',"phone":"800-555-1212"}
		                  ];
		var formData={};
		formData.customerListStr=JSON.stringify(customerList);
		
		var uri = '/CustomerController/receiveCustomerListStr';
		$.ajax({
			url: uri,
			method: 'POST',
			dataType: 'JSON',
			data: formData
		}).success(function(result) {
			$scope.$apply(function() {
				$scope.ajaxResult = result;
			})
		})
	}
	
	$scope.getData=function(){
	var uri = '/CustomerController/data';
		
		$.ajax({
			url: uri,
			method: 'get',
			dataType: 'json',
			data: null
		}).success(function(result) {
			$scope.$apply(function() {
				//$scope.totalItems = result.totalRows;
				// 更新表格数据
				$scope.ajaxResultData = result.data;
				$scope.ajaxResult = result;
			})
		})
	}
	
	$scope.getCustomerEntity=function(){
		var uri = '/CustomerController/getCustomerEntity';
			
			$.ajax({
				url: uri,
				method: 'get',
				dataType: 'json',
				data: null
			}).success(function(result) {
				$scope.$apply(function() {
					$scope.ajaxResultData = result.data;
					$scope.ajaxResult = result;
				})
			})
		}
	
	$scope.getCustomerList=function(){
		var uri = '/CustomerController/getCustomerList?';
		uri=uri+"&pageSize="+$scope.pageSize;
		uri=uri+"&currPage="+$scope.currPage;
		
		$.ajax({
			url: uri,
			method: 'get',
			dataType: 'json',
			data: $scope.formData
		}).success(function(result) {
			$scope.$apply(function() {
				$scope.customerList = result;
				$scope.ajaxResult = result;
			})
		})
	}
});
</script>
</body>

</html>