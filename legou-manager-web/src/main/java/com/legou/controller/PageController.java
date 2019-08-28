package com.legou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	//页面跳转的通用方法
	@RequestMapping("/{page}")//restful风格
	//根据路径变量接收传递的参数
	public String page(@PathVariable String page) {		
		return page;
	}
	
	@RequestMapping("/index")
	public String index() {
		
		return "index";
	}

}
