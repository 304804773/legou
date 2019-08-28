package com.legou.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.legou.item.pojo.ItemDetail;
import com.legou.pojo.TbItem;
import com.legou.pojo.TbItemDesc;
import com.legou.service.ItemService;

@Controller
public class ItemDetailController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemid}")
	public String getItemDetailInfo(@PathVariable Long itemid,Model model) {
		//根据id查找item
		TbItem item = itemService.geTbItemById(itemid);
		//根据找出来的item，新建一个itemDetail
		ItemDetail itemDetail=new ItemDetail(item);
		//根据id查找itemDesc
		TbItemDesc itemDesc=itemService.getItemDescById(itemid);
		//添加到model
		model.addAttribute("item", itemDetail);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
	}

}
