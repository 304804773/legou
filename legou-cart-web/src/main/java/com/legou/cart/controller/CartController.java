package com.legou.cart.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.cart.service.LegouCartService;
import com.legou.cart.service.OrderItemService;
import com.legou.cart.service.OrderService;
import com.legou.common.redis.JedisClient;
import com.legou.common.utils.CookieUtils;
import com.legou.common.utils.JsonUtils;
import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbItem;
import com.legou.pojo.TbOrder;
import com.legou.pojo.TbUser;
import com.legou.service.ItemService;

@Controller
public class CartController {
	@Autowired
	private ItemService itemService;
	@Autowired
	private LegouCartService legouCartService;

	public List<TbItem> getCookieItem(HttpServletRequest request) {
		// 得到客户端中的cookieid为legoucart的值并且编码
		String jsoncookie = CookieUtils.getCookieValue(request, "legoucart", true);
		// 如果为空
		if (StringUtils.isBlank(jsoncookie)) {
			// 返回一个新的集合
			return new ArrayList<TbItem>();
		}
		// 如果不为空将里面的json转换为集合
		List<TbItem> items = JsonUtils.jsonToList(jsoncookie, TbItem.class);
		return items;
	}

	// 添加商品到购物车
	@RequestMapping("/cart/add/{itemid}")
	public String addItemToCart(@PathVariable Long itemid, @RequestParam(defaultValue = "1") Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		// 得到保存在cookie中的商品集合
		List<TbItem> items = getCookieItem(request);
		TbUser user = (TbUser) request.getAttribute("legouuser");
		if (user != null) {
			legouCartService.addItemTocart(user.getId(), itemid, num);
			return "cartSuccess";
		}
		// 写一个判断标记
		boolean flag = false;
		// 遍历集合，当客户又重新添加一样的商品避免cookie中的商品被覆盖掉
		for (TbItem tbItem : items) {
			if (tbItem.getId().longValue() == itemid) {
				// 设置商品数量为原来的数量加后来添加的数量
				tbItem.setNum(tbItem.getNum() + num);
				flag = true;
			}
		}
		// 当cookie中没有值时从数据库中拿
		if (!flag) {
			// 根据前端传来的id从数据库中查询取得商品信息
			TbItem item = itemService.geTbItemById(itemid);
			item.setNum(num);
			// 判断item中是否有图片
			if (StringUtils.isNotBlank(item.getImage())) {
				// 得到所有图片的地址并将它们转换为字符串
				String[] imagepath = item.getImage().split(",");
				// 选择数组中的第一个下标当做地址
				item.setImage(imagepath[0]);
			}
			// 把商品添加到集合
			items.add(item);
		}
		// 把商品集合存储到cookie中
		CookieUtils.setCookie(request, response, "legoucart", JsonUtils.objectToJson(items), 30 * 60 * 60, true);

		return "cartSuccess";
	}

	// 去购物车结算
	@RequestMapping("cart/cart")
	public String tocart(HttpServletRequest request, HttpServletResponse response) {
		// 得到cookie中的值
		List<TbItem> cookieItem = getCookieItem(request);
		// 判断用户有没有登录
		TbUser user = (TbUser) request.getAttribute("legouuser");
		if (user != null) {
			legouCartService.moveCartToRedis(user.getId(), cookieItem);
			CookieUtils.deleteCookie(request, response, "legoucart");
			cookieItem = legouCartService.getItemCart(user.getId());
		}
		// 把购物车加入到request中
		request.setAttribute("cartList", cookieItem);
		return "cart";
	}

	// 更新购物车的商品数量
	@RequestMapping("/cart/update/num/{itemid}/{num}")
	@ResponseBody
	public LegouResult updateCart(@PathVariable Long itemid, @PathVariable Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		// 得到cookie中的商品集合
		List<TbItem> cookieItem = getCookieItem(request);
		// 判断用户有没有登录
		TbUser user = (TbUser) request.getAttribute("legouuser");
		if (user != null) {
			legouCartService.updateItemNum(user.getId(),itemid,num);
			return LegouResult.ok();
		}
		// 遍历集合
		for (TbItem tbItem : cookieItem) {
			// 如果集合中商品的id与前端传来的id一样
			if (tbItem.getId() == itemid.longValue()) {
				// 设置商品的数量
				tbItem.setNum(num);
			}
		}
		// 把商品集合存储到cookie中
		CookieUtils.setCookie(request, response, "legoucart", JsonUtils.objectToJson(cookieItem), 30 * 60 * 60, true);
		return LegouResult.ok();
	}

	// 删除购物车中商品
	@RequestMapping("/cart/delete/{itemid}")
	public String deleteItemById(HttpServletRequest request, HttpServletResponse response, @PathVariable Long itemid) {
		// 得到cookie中的商品集合
		List<TbItem> cookieItem = getCookieItem(request);
		// 判断用户有没有登录
		TbUser user = (TbUser) request.getAttribute("legouuser");
		if (user != null) {
			legouCartService.deleteItem(user.getId(),itemid);
			return "redirect:/cart/cart.html";
		}
		// 遍历集合
		for (TbItem tbItem : cookieItem) {
			// 如果集合中商品的id与前端传来的id一样
			if (tbItem.getId() == itemid.longValue()) {
				// 删除
				cookieItem.remove(tbItem);
				// 跳出循环
				break;
			}
		}
		CookieUtils.setCookie(request, response, "legoucart", JsonUtils.objectToJson(cookieItem), 30 * 60 * 60, true);
		// 重定向
		return "redirect:/cart/cart.html";
	}
}
