package com.legou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.common.pojo.EasyUIDataGridResult;
import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbItem;
import com.legou.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{id}")
	@ResponseBody
	public TbItem geTbItemById(@PathVariable long id) {
		TbItem item=itemService.geTbItemById(id);
		System.out.println(item.getCid());
		return item;
	}
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(int page,int rows) {
		//传给界面一个json数据
		return itemService.getItemList( page, rows);
	}
	@RequestMapping("/item/save")
	@ResponseBody
	public LegouResult saveItem(TbItem tbItem,String desc){
		System.out.println("wqewqe");
		System.out.println(desc);
		LegouResult leoLegouResul = itemService.save(tbItem,desc);
		
		return leoLegouResul;
	}
}
