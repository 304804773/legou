package com.legou.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.legou.common.redis.JedisClient;
import com.legou.common.utils.JsonUtils;
import com.legou.common.utils.LegouResult;
import com.legou.mapper.TbUserMapper;
import com.legou.pojo.TbUser;
import com.legou.pojo.TbUserExample;
import com.legou.pojo.TbUserExample.Criteria;
import com.legou.sso.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private TbUserMapper tbUserMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;

	@Override
	public LegouResult login(String username, String password) {
		System.out.println(username+""+password);
		//创建查询条件
		TbUserExample example=new TbUserExample();
		Criteria criteria=example.createCriteria();
		//设置查询条件等于username
		criteria.andUsernameEqualTo(username);
		//根据查询条件从数据库中得到对应的集合
		List<TbUser> list = tbUserMapper.selectByExample(example);
		//判断集合中是否有值
		if(list==null||list.size()==0) {
			//如果查询到有值进入方法，返回用户名已存在
			return LegouResult.build(400, "用户名不存在");
		}
		//如果用户名不存在继续判断输入的密码是否与数据库中的密码匹配
		//得到集合中的第一个数据
		TbUser user = list.get(0);
		//进行密码判断
		if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			System.out.println("密码错误");
			return LegouResult.build(400, "密码错误");
		}
		//得到一个随机字符
		String token = UUID.randomUUID().toString();
		System.out.println(token);
		//往缓存中添加数据
		jedisClient.set("SESSION"+token, JsonUtils.objectToJson(user));
		System.out.println("缓存"+""+"SESSION"+token);
		//设置缓存存在时间
		jedisClient.expire("SESSION" + token, SESSION_EXPIRE);
		//返回一个id
		return LegouResult.ok(token);
	}

}
