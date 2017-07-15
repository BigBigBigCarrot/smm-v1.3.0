<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/static/js/widget/angular.min.js"></script>
<script src="/static/js/widget/angular-file-upload.min.js"></script>
<script src="/static/js/widget/jquery-1.11.1.min.js"></script>
<title>fileUploadPage</title>
</head>
<body ng-app="myApp">

<div ng-controller="firstController">

<input type="file" class="upload" nv-file-select="" uploader="uploader" multiple=""/>

</div>


</body>
<script>
var myApp=angular.module('myApp',['angularFileUpload']);
myApp.controller('firstController',['$scope','FileUploader',function($scope,FileUploader){
	function init(){
		
	}
	init();
	
	var BASE_URL="";
	
	var uploader = $scope.uploader = new FileUploader({
        url: BASE_URL + '/FileUploadController/upload',
        autoUpload: true
    });
	
	/*
    uploader.filters.push({
      name: 'imageFilter',
      fn: function(item, options) {
        var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
        return '|vnd.ms-excel|vnd.openxmlformats-officedocument.spreadsheetml.sheet|xlsx|'.indexOf(type) !== -1;
      }
    });

    // // CALLBACKS
    uploader.onSuccessItem = function(fileItem, response, status, headers) {
    	layer.msg(response.msg);
    	getPageData();
    	//$scope.select();
    };

    uploader.onWhenAddingFileFailed = function(item, filter, options) {
      layer.msg('格式错误');
    };
    
    $scope.setUploadParam=function(){
  	  
  	  uploader.onBeforeUploadItem = function(fileItem) {
      fileItem.formData = [{
    	  'merchantId': $scope.merchantId,
    	  'distributorId': $scope.distributorId,
    	  'distributorShopId': $scope.distributorShopId
      }]
    }
    }
	*/
}]);
</script>

</html>