package com.legou.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.cart.service.LegouCartService;
import com.legou.common.redis.JedisClient;
import com.legou.common.utils.JsonUtils;
import com.legou.mapper.TbItemMapper;
import com.legou.pojo.TbItem;
@Service
public class LegouCartServiceImpl implements LegouCartService {
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TbItemMapper tbItemMapper;

	@Override
	public void addItemTocart(Long id, Long itemid,Integer num) {
		//首先判断商品在缓存中是否存在
		Boolean hexists = jedisClient.hexists("legou" + id, itemid + "");
		//如果存在
		if(hexists) {
			String itemjson = jedisClient.hget("legou" + id, itemid + "");
			TbItem tbitem = JsonUtils.jsonToPojo(itemjson, TbItem.class);
			tbitem.setNum(tbitem.getNum() + num);
			jedisClient.hset("legou" + id, itemid + "", JsonUtils.objectToJson(tbitem));
			
		}
		TbItem item = tbItemMapper.selectByPrimaryKey(itemid);
		item.setNum(num);
		if(StringUtils.isNotBlank(item.getImage())) {
			String[] imagepathString = item.getImage().split(",");
			item.setImage(imagepathString[0]);	
		}
		jedisClient.hset("legou" + id, itemid + "", JsonUtils.objectToJson(item));
	}

	@Override
	public void moveCartToRedis(Long id, List<TbItem> cookieItem) {	
		for (TbItem tbItem : cookieItem) {
			addItemTocart(id, tbItem.getId(), tbItem.getNum());
		}
		
	}

	@Override
	public List<TbItem> getItemCart(Long id) {
		List<String> hvals = jedisClient.hvals("legou" + id);
		List<TbItem> items=new ArrayList<TbItem>();
		for (String string : hvals) {
			TbItem item= JsonUtils.jsonToPojo(string, TbItem.class);
			items.add(item);
		}
	
		
		return items;
	}

	@Override
	public void deleteItem(Long id, Long itemid) {
		jedisClient.hdel("legou" + id, itemid+"");
		
	}

	@Override
	public void updateItemNum(Long id, Long itemid, Integer num) {
		String hget = jedisClient.hget("legou" + id, itemid+"");
		TbItem item = JsonUtils.jsonToPojo(hget, TbItem.class);
		item.setNum(num);
		jedisClient.hset("legou" + id, itemid + "", JsonUtils.objectToJson(item));
	
	}

}
