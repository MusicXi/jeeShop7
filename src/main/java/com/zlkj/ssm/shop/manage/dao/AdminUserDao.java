package com.zlkj.ssm.shop.manage.dao;

import com.zlkj.ssm.shop.core.DaoManager;
import com.zlkj.ssm.shop.core.entity.User;


public interface AdminUserDao extends DaoManager<User> {

	public User selectOneByCondition(User user);

	public Integer selectCount(User user);

}
