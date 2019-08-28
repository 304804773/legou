package com.legou.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.common.pojo.EasyUiTreeNode;
import com.legou.service.ItemCatService;

@Controller
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	//查询所有商品类目
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUiTreeNode> getItemCat(@RequestParam(name="id",defaultValue="0") long id){
		List<EasyUiTreeNode> itemCatList=itemCatService.getgetItemCatList(id);
		return itemCatList;
	}

}
