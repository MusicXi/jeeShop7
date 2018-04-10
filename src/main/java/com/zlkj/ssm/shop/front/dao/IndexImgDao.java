package com.zlkj.ssm.shop.front.dao;

import java.util.List;

import com.zlkj.ssm.shop.core.DaoManager;
import com.zlkj.ssm.shop.front.entity.IndexImg;
public interface IndexImgDao extends DaoManager<IndexImg> {

	/**
	 * @param i
	 * @return
	 */
	List<IndexImg> getImgsShowToIndex(int i);

}
