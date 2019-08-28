package com.legou.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.common.pojo.EasyUiTreeNode;
import com.legou.common.utils.LegouResult;
import com.legou.content.service.ContentCategoryService;

@Controller
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	//查询所有广告类目
	@RequestMapping("/content/category/list")
	@ResponseBody
	//返回json数据，设置没有id值时候，默认为0
	public List<EasyUiTreeNode> getContentCatgoryList(@RequestParam(name="id",defaultValue="0") long id){
		List<EasyUiTreeNode> contentList= contentCategoryService.getConteCategoryList(id);
		return contentList;
	}
	
	//添加广告节点
	@RequestMapping("/content/category/create")
	@ResponseBody
	public LegouResult  createContentNode(Long parentId,String name) {
		LegouResult legouResult=contentCategoryService.addContentNode(parentId,name);
		return legouResult;
	}
	//重命名
	@RequestMapping("/content/category/update ")
	@ResponseBody
	public LegouResult updateContentNode(Long id,String name) {
		LegouResult legouResult=contentCategoryService.updateContentNode(id,name);
		return legouResult;
	}
	
}
