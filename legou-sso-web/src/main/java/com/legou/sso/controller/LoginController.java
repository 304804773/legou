package com.legou.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.common.utils.CookieUtils;
import com.legou.common.utils.LegouResult;
import com.legou.sso.service.LoginService;

@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;
	@RequestMapping("/page/login")
	public String toLogin() {
		return "login";
	}
	@RequestMapping("/user/login")
	@ResponseBody
	public LegouResult login(String username,String password,HttpServletRequest request,HttpServletResponse response) {
		LegouResult legouResult=loginService.login(username,password);
		//得到legouResult的状态
		if(legouResult.getStatus() == 200) {
			//得到legouResult传来的id
			String token = legouResult.getData().toString();
			//设置cookies的属性
			CookieUtils.setCookie(request, response, TOKEN_KEY, token);
		}
		return legouResult;
	}
}
