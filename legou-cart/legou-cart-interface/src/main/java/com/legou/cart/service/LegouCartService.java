package com.legou.cart.service;

import java.util.List;

import com.legou.pojo.TbItem;

public interface LegouCartService {

	void addItemTocart(Long id, Long itemid, Integer num);

	void moveCartToRedis(Long id, List<TbItem> cookieItem);

	List<TbItem> getItemCart(Long id);

	void deleteItem(Long id, Long itemid);

	void updateItemNum(Long id, Long itemid, Integer num);

}
