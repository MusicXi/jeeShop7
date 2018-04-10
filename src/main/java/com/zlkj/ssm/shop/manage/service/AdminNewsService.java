package com.zlkj.ssm.shop.manage.service;

import java.util.List;

import com.zlkj.ssm.shop.core.Services;
import com.zlkj.ssm.shop.manage.entity.News;
public interface AdminNewsService extends Services<News> {
	public List<News> selecIndexNews(News e);

	/**
	 * @param ids
	 * @param status 2:审核通过,4:审核未通过
	 */
	public void updateStatus(String[] ids, String status);
	
	/**
	 * 更新指定的文章 显示/不显示
	 * @param news
	 */
	public void updateDownOrUp(News news);

	public int selectCount(News news);
}
