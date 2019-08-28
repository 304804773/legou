var LEGOUMALL = {
	checkLogin : function(){
		var _ticket = $.cookie("token");
		alert(_ticket);
		if(!_ticket){
			return ;
		}
		
		$.ajax({
			//_ticket为存在redis中的key
			url : "http://localhost:8088/user/token/" + _ticket,
			dataType : "jsonp",
			jsonp:"callback",
			type : "GET",
			success : function(data){
				if(data.status == 200){
					//从json中的数据的username
					var username = data.data.username;
					var html = username + "，欢迎来到乐购购物网！<a href=\"http://www.legou.cn/user/logout.html\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	LEGOUMALL.checkLogin();
});