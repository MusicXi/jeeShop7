package com.zlkj.ssm.shop.manage.dao;
import java.util.List;

import com.zlkj.ssm.shop.core.DaoManager;
import com.zlkj.ssm.shop.manage.entity.IndexImg;
public interface AdminIndexImgDao extends DaoManager<IndexImg> {
	/**
	 * @param i
	 * @return
	 */
	List<IndexImg> getImgsShowToIndex(int i);

}
