package com.legou.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.legou.common.redis.JedisClient;
import com.legou.common.utils.JsonUtils;
import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbUser;
import com.legou.sso.service.TokenService;
@Service
public class TokenServiceImpl implements TokenService {
	@Autowired
	private JedisClient jedisClient;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;

	@Override
	public LegouResult getUserByToken(String _ticket) {
		//根据Jessionid从redis中搜索key
		String string = jedisClient.get("SESSION"+_ticket);
		System.out.println(string);
		//取不到用户信息，登录已经过期，返回登录过期
		if (StringUtils.isBlank(string)) {
			return LegouResult.build(201, "用户登录已经过期");
		}
		//取到用户信息更新token的过期时间
		jedisClient.expire("SESSION" + _ticket, SESSION_EXPIRE);
		TbUser user = JsonUtils.jsonToPojo(string, TbUser.class);		
		return LegouResult.ok(user);
	}

}
