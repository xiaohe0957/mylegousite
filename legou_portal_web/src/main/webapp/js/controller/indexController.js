app.controller("indexController",function($scope,$http){

	// 点击搜索按钮
	$scope.search=function(){
		// 如果用户没有输入内容，自己指定搜索的关键字
		if($scope.keyword==null || $scope.keyword==""){
			// 设置默认值
			$scope.keyword="小米";
		}
		// 跳转页面，发送请求
		// http://localhost:8084/search.html#?keyword=手机
		// # angularJS特殊的写法 使用angularJS服务获取到地址栏上的参数
		location.href="http://localhost:8084/search.html#?keyword="+$scope.keyword;
	}
	
})