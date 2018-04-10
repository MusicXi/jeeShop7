/**
 * 2012-7-8
 * jqsl2012@163.com
 */
package com.zlkj.ssm.shop.manage.dao;

import java.util.List;

import com.zlkj.ssm.shop.core.DaoManager;
import com.zlkj.ssm.shop.manage.entity.News;
public interface AdminNewsDao extends DaoManager<News> {

	/**
	 * @param e
	 * @return
	 */
	List<News> selecIndexNews(News e);

	/**
	 * @param news
	 */
	void sync(News news);

	void updateDownOrUp(News news);

	int selectCount(News news);

}
