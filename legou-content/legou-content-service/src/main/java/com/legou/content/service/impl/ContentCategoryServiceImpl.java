package com.legou.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.common.pojo.EasyUiTreeNode;
import com.legou.common.utils.LegouResult;
import com.legou.content.service.ContentCategoryService;
import com.legou.mapper.TbContentCategoryMapper;
import com.legou.pojo.TbContentCategory;
import com.legou.pojo.TbContentCategoryExample;
import com.legou.pojo.TbContentCategoryExample.Criteria;
import com.legou.pojo.TbContentExample;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;

	@Override
	public List<EasyUiTreeNode> getConteCategoryList(long id) {
		//新建广告类目
		TbContentCategoryExample example = new TbContentCategoryExample();
		//新建条件
		Criteria criteria = example.createCriteria();
		//根据id来获得条件
		criteria.andParentIdEqualTo(id);
		//查询的到广告类目集合
		List<TbContentCategory> selectByExample = tbContentCategoryMapper.selectByExample(example);
		//建立树集合
		List<EasyUiTreeNode> nodelisTreeNodes=new ArrayList<EasyUiTreeNode>();
		//遍历广告类目集合
		for (TbContentCategory tbContentCategory : selectByExample) {
			//兴建树
			EasyUiTreeNode easyUiTreeNode=new EasyUiTreeNode();
			//设置树的id为广告类目的id
			easyUiTreeNode.setId(tbContentCategory.getId());
			//设置树的文本值为广告类目的名字
			easyUiTreeNode.setText(tbContentCategory.getName());
			//设置树的状态为广告类目的是否为父亲节点，是closed，不是open
			easyUiTreeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
			//添加树到树集合
			nodelisTreeNodes.add(easyUiTreeNode);
		}
		return nodelisTreeNodes;
	}

	@Override
	public LegouResult addContentNode(Long parentId, String name) {
		//新建广告类目
		TbContentCategory tbContentCategory=new TbContentCategory();
		//因为是新建的广告类目，里面肯定没有子节点所以肯定是没有不是父亲
		tbContentCategory.setIsParent(false);
		//设置它的名字属性
		tbContentCategory.setName(name);
		//设置它属于哪个叶子节点id
		tbContentCategory.setParentId(parentId);
		//设置它的状态
		tbContentCategory.setStatus(1);
		//设置它的排序顺序
		tbContentCategory.setSortOrder(1);
		//设置它的创建时间
		tbContentCategory.setCreated(new Date());
		//设置它的更改时间
		tbContentCategory.setUpdated(new Date());
		//数据库中创建
		tbContentCategoryMapper.insert(tbContentCategory);
		//根据自动绑定到对象上查询返回的id查询得到对象
		TbContentCategory selectByPrimaryKey = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		//如果的得到的对象不是一个父亲节点
		if(!selectByPrimaryKey.getIsParent()) {
			//设置把它变成一个父亲节点
			selectByPrimaryKey.setIsParent(true);
			//更新数据库信息
			tbContentCategoryMapper.updateByPrimaryKey(selectByPrimaryKey);
		}
		return LegouResult.ok(tbContentCategory);
	}

	@Override
	public LegouResult updateContentNode( Long id, String name) {
		//根据id得到广告类目
		TbContentCategory selectByPrimaryKey = tbContentCategoryMapper.selectByPrimaryKey(id);
		//设置新的名字
		selectByPrimaryKey.setName(name);
		//修改数据库内容
		tbContentCategoryMapper.updateByPrimaryKey(selectByPrimaryKey);
		return LegouResult.ok();
	}

}
