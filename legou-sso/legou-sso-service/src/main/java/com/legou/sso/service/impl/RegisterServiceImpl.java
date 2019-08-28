package com.legou.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.legou.common.utils.LegouResult;
import com.legou.mapper.TbUserMapper;
import com.legou.pojo.TbContentCategoryExample.Criteria;
import com.legou.pojo.TbUser;
import com.legou.pojo.TbUserExample;
import com.legou.pojo.TbUserExample.Criterion;
import com.legou.sso.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {
	@Autowired
	private TbUserMapper tbUserMapper;

	@Override
	public void saveUser(TbUser user) {
		user.setCreated(new Date());
		user.setUpdated(new Date());
		tbUserMapper.insert(user);

	}

	@Override
	public LegouResult checkout(String param, Integer type) {
		TbUserExample example=new TbUserExample();
		com.legou.pojo.TbUserExample.Criteria criteria=example.createCriteria();
		if(type==1) {
			criteria.andUsernameEqualTo(param);
		}else if(type==2) {
			criteria.andPhoneEqualTo(param);
		}else if(type==3) {
			criteria.andEmailEqualTo(param);
		}else {
			return LegouResult.build(400, "添加失败");
		}
		List<TbUser> userList = tbUserMapper.selectByExample(example);
		if(userList!=null&&userList.size()>0) {
			return LegouResult.ok(false);
		}
		
		return LegouResult.ok(true);
	}

	@Override
	public LegouResult register(TbUser user) {
		LegouResult legouResult=new LegouResult();
		LegouResult checkout = checkout(user.getUsername(),1);
		if(!(boolean) checkout.getData()) {
			return LegouResult.build(400, "用户名已存在");
		}
		checkout=checkout(user.getPhone(),2);
		if(!(boolean) checkout.getData()) {
			return LegouResult.build(400, "电话号码已存在");
		}
		user.setCreated(new Date());
		user.setUpdated(new Date());
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		tbUserMapper.insert(user);
		
		return LegouResult.ok();
	}

}
