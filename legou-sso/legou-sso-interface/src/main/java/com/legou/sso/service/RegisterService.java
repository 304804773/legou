package com.legou.sso.service;

import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbUser;

public interface RegisterService {

	void saveUser(TbUser user);

	LegouResult checkout(String param, Integer type);

	LegouResult register(TbUser user);

}
