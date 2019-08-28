package com.legou.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.legou.common.redis.JedisClientPool;
import com.legou.common.utils.JsonUtils;
import com.legou.common.utils.LegouResult;
import com.legou.content.service.ContentService;
import com.legou.mapper.TbContentMapper;
import com.legou.pojo.TbContent;
import com.legou.pojo.TbContentExample;
import com.legou.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private JedisClientPool jedisClientPool;

	@Override
	public LegouResult saveContent(TbContent tbContent) {
		// 设置创建时间
		tbContent.setCreated(new Date());
		// 设置更新时间
		tbContent.setUpdated(new Date());
		// 数据库中创建广告
		tbContentMapper.insertSelective(tbContent);
		//设置每次创建的时候都把缓存删除
		jedisClientPool.hdel("CONTENT_LIST", tbContent.getCategoryId().toString());
		// 返回需要响应的json
		return LegouResult.ok();
	}

	@Override
	public List<TbContent> getContentByCateId(long categoryid) {
		// 从缓存中拿数据
		String hget = jedisClientPool.hget("CONTENT_LIST", categoryid + "");
		// 判断缓存是否为空
		if (!StringUtils.isEmpty(hget)) {
			//如果非空，从缓存中的map集合hegt中用反射的机制得到key，来得到value
			return JsonUtils.jsonToList(hget,TbContent.class);
		}
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryid);
		List<TbContent> selectByExample = tbContentMapper.selectByExample(example);
		// 往数据库中添加缓存，CONTENT_LIST为创建的map集合的名字，categoryid + ""为key
		//用JsonUtils.objectToJson方法将集合装换为json格式作为value
		jedisClientPool.hset("CONTENT_LIST", categoryid + "", JsonUtils.objectToJson(selectByExample));
		return selectByExample;
	}

}
