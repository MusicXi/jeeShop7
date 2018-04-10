package com.zlkj.ssm.shop.manage.service;
import java.util.List;

import com.zlkj.ssm.shop.core.Services;
import com.zlkj.ssm.shop.manage.entity.IndexImg;
public interface AdminIndexImgService extends Services<IndexImg> {
	/**
	 * 加载图片显示到门户
	 * @param i
	 */
	List<IndexImg> getImgsShowToIndex(int i);

}
