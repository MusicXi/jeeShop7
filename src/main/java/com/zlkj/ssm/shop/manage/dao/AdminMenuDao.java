package com.zlkj.ssm.shop.manage.dao;
import java.util.List;
import java.util.Map;

import com.zlkj.ssm.shop.core.DaoManager;
import com.zlkj.ssm.shop.core.entity.Menu;

public interface AdminMenuDao extends DaoManager<Menu> {
	public List<Menu> selectList(Map<String, String> param);
	public List<Menu> selectMenus(Menu menu);
	public int getCount(Menu menu);
	public List<Menu> selectMenus(Map<String, String> param);
	
}
