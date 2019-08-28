package com.legou.content.controller;


import java.nio.channels.FileChannel.MapMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legou.common.redis.JedisClientPool;
import com.legou.common.utils.JsonUtils;
import com.legou.content.service.ContentService;
import com.legou.pojo.TbContent;

@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;

	@RequestMapping("/index")
	public String index(Model model) {
		long categoryid=89;

		List<TbContent> contentByCateId = contentService.getContentByCateId(categoryid);
		
		model.addAttribute("aD1List",contentByCateId);

		return "index";
	}
}
