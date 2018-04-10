package com.zlkj.ssm.shop.manage.service;

import com.zlkj.ssm.shop.core.Services;
import com.zlkj.ssm.shop.core.entity.User;
public interface AdminUserService extends Services<User> {
	/**
	 * @param e
	 * @return
	 */
	public User login(User e);
}
