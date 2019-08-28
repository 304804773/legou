package com.legou.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.common.pojo.EasyUiTreeNode;
import com.legou.mapper.TbItemCatMapper;
import com.legou.pojo.TbItemCat;
import com.legou.pojo.TbItemCatExample;
import com.legou.pojo.TbItemCatExample.Criteria;
import com.legou.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public List<EasyUiTreeNode> getgetItemCatList(long id) {
		TbItemCatExample itemCatExample = new TbItemCatExample();
		//创建itemCatExample内部类条件
		Criteria createCriteria = itemCatExample.createCriteria();
		//调用内部类的方法
		createCriteria.andParentIdEqualTo(id);
		// 查询出所有类目集合
		List<TbItemCat> selectByExample = itemCatMapper.selectByExample(itemCatExample);
		// 建立需要传送的json集合
		List<EasyUiTreeNode> listNodes = new ArrayList<EasyUiTreeNode>();
		//foreach遍历selectByExample集合
		for (TbItemCat tbItemCat : selectByExample) {
			EasyUiTreeNode easyUiTreeNode=new EasyUiTreeNode();
			//设置id
			easyUiTreeNode.setId(tbItemCat.getId());
			//设置名字
			easyUiTreeNode.setText(tbItemCat.getName());
			//设置是否是一个父类目，tree是closed，flose是open
			easyUiTreeNode.setState(tbItemCat.getIsParent()?"closed":"open");
			//将easyUiTreeNode添加到listNodes集合
			listNodes.add(easyUiTreeNode);
		}

		return listNodes;
	}

}
