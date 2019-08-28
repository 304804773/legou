package com.legou.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.common.utils.LegouResult;
import com.legou.sso.service.TokenService;

@Controller
public class TokenController {
	
	@Autowired
	private TokenService tokenService;
	@RequestMapping("user/token/{_ticket}")
	@ResponseBody
	public Object tokenLogin(@PathVariable String _ticket,String callback) {
		LegouResult result = tokenService.getUserByToken(_ticket);
		System.out.println(callback);
		//jsonp回调函数callback会传来一串字符，用isNotBank进行判断某字符串是否不为空且长度不为0且不由空白符(whitespace)构成
		if (StringUtils.isNotBlank(callback)) {
			//使用Spring自带对象，前提是需要在Srping4.0的版本才有，把结果封装成一个js语句响应
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			//如果用户登陆过就替他直接登录
			return mappingJacksonValue;
		}
		//没有登陆过
		return result;
	}

}
