package com.zlkj.ssm.shop.front.service;
import java.util.List;

import com.zlkj.ssm.shop.core.Services;
import com.zlkj.ssm.shop.front.entity.IndexImg;

public interface IndexImgService extends Services<IndexImg> {

	/**
	 * 加载图片显示到门户
	 * @param i
	 */
	List<IndexImg> getImgsShowToIndex(int i);

}
