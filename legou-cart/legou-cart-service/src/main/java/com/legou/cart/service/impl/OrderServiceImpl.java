package com.legou.cart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.cart.service.OrderService;
import com.legou.mapper.TbOrderMapper;
import com.legou.pojo.TbOrder;
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private TbOrderMapper tbOrderMapper;
		@Override
		public void addOrder(TbOrder order) {
			tbOrderMapper.insert(order);
			System.out.println(order.getOrderId());
			
		}

}
