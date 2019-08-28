package com.legou.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.legou.common.redis.JedisClient;
import com.legou.common.utils.CookieUtils;
import com.legou.common.utils.JsonUtils;
import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbUser;
import com.legou.sso.service.TokenService;

public class Interceptor implements HandlerInterceptor {
@Autowired
private TokenService tokenService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//得到页面request照片中token的值
		String cookieValue = CookieUtils.getCookieValue(request, "token");
		//如果token为空，放行
		if(StringUtils.isBlank(cookieValue)) {			
			return true;
		}
		//判断用户的状态
		LegouResult userByToken = tokenService.getUserByToken(cookieValue);
		if(userByToken.getStatus()!=200) {
			return true;
		}
		//将数据信息转换为对象
		TbUser user=(TbUser) userByToken.getData();
		request.setAttribute("legouuser", user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
