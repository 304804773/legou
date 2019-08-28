package com.legou.sso.controller;

import java.io.UnsupportedEncodingException;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbUser;
import com.legou.sso.service.RegisterService;

@Controller
public class RegisterController {
	@Autowired
	private RegisterService registerService;
	
	@RequestMapping("toRegister")
	public String toRegister() {
		return "register";
	}
	
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public LegouResult registerSuccess(@PathVariable String param,@PathVariable String type) throws Exception {
		System.out.println(type);
		LegouResult legouResult=registerService.checkout(param,Integer.parseInt(type));
		return legouResult;
	}
	
	@RequestMapping(value="/user/register")
	@ResponseBody
	public LegouResult register(TbUser user) {
		LegouResult legouResult = registerService.register(user);
		return legouResult;
	}


	
	

}
