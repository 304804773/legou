package com.legou.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.legou.common.pojo.EasyUIDataGridResult;
import com.legou.common.redis.JedisClient;
import com.legou.common.utils.IDUtils;
import com.legou.common.utils.JsonUtils;
import com.legou.common.utils.LegouResult;
import com.legou.mapper.TbItemDescMapper;
import com.legou.mapper.TbItemMapper;
import com.legou.pojo.TbItem;
import com.legou.pojo.TbItemDesc;
import com.legou.pojo.TbItemExample;
import com.legou.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private Destination activeMQTopic;
	@Autowired
	private JedisClient jedisClient;

	@Override
	public TbItem geTbItemById(long id) {
		// 先从缓存中得到数据
		String itemDescString = jedisClient.get(id + "detail");
		// 如果字符串的地址是true
		if (!(itemDescString == null)) {
			System.out.println("从缓存中拿");
			// 用自己编写的工具包将json类型的数据转变为TbItem
			TbItem tbItem = JsonUtils.jsonToPojo(itemDescString, TbItem.class);
			return tbItem;
		}
		TbItem tbItem = itemMapper.selectByPrimaryKey(id);
		// 如果缓存中没有数据，就把数据添加到缓存中
		jedisClient.set(id + "detail", JsonUtils.objectToJson(tbItem));
		// 设置redis过期时间
		jedisClient.expire(id + "detail", 3600);
		System.out.println("从数据库拿");
		return tbItem;
	}

	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		// 设置分页大小
		PageHelper.startPage(page, rows);
		TbItemExample example = new TbItemExample();
		// 查询出所有信息
		List<TbItem> selectByExample = itemMapper.selectByExample(example);
		// 用分页包装
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(selectByExample);
		// 实例化工具类
		EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
		// 设置当前页信息，easyui会自动解析，返回一个json数组
		easyUIDataGridResult.setRows(selectByExample);
		// 设置多少行
		easyUIDataGridResult.setTotal(pageInfo.getTotal());
		return easyUIDataGridResult;
	}

	@Override
	public LegouResult save(TbItem tbItem, String desc) {
		// 得到一个随机Id
		long itemId = IDUtils.genItemId();
		// 设置商品Id
		tbItem.setId(itemId);
		tbItem.setStatus((byte) 1);
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		// 数据库添加商品
		itemMapper.insert(tbItem);
		System.out.println(desc);
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(itemId);
		System.out.println(tbItemDesc.getItemId());
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		// 数据库添加商品描述
		tbItemDescMapper.insert(tbItemDesc);
		// 向mq中添加数据
		jmsTemplate.send(activeMQTopic, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(itemId + "");
			}
		});
		// 返回插入成功
		return LegouResult.ok();
	}

	@Override
	public TbItemDesc getItemDescById(Long itemid) {
		// 先从缓存中得到数据
		String itemDescString = jedisClient.get(itemid + "desc");
		if (!(itemDescString == null)) {
			TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(itemDescString, TbItemDesc.class);
			System.out.println("从缓存中拿");
			return tbItemDesc;
		}
		TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemid);
		// 如果缓存中没有数据就将数据添加到缓存中
		jedisClient.set(itemid + "desc", JsonUtils.objectToJson(tbItemDesc));
		// 设置过期时间
		jedisClient.expire(itemid + "desc", 3600);
		System.out.println("从数据库拿");
		return tbItemDesc;
	}

}
