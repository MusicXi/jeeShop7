/**
 * 2012-7-8
 * jqsl2012@163.com
 */
package com.zlkj.ssm.shop.front.dao;

import java.util.List;

import com.zlkj.ssm.shop.core.DaoManager;
import com.zlkj.ssm.shop.front.entity.News;
public interface NewsDao extends DaoManager<News> {

	/**
	 * @param e
	 * @return
	 */
	List<News> selecIndexNews(News e);

	/**
	 * @return
	 */
	List<String> selectAllMd5();

	/**
	 * @param e
	 */
	void updateInBlackList(String e);

	/**
	 * @param news
	 */
	void sync(News news);

	List<News> selectNoticeList(News news);

	News selectSimpleOne(News news);

}
