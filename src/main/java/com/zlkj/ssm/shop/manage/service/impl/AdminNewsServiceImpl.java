package com.zlkj.ssm.shop.manage.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlkj.ssm.shop.core.ServersManager;
import com.zlkj.ssm.shop.manage.dao.AdminNewsDao;
import com.zlkj.ssm.shop.manage.entity.News;
import com.zlkj.ssm.shop.manage.service.AdminNewsService;

import javax.annotation.Resource;
@Transactional
@Service
public class AdminNewsServiceImpl extends ServersManager<News, AdminNewsDao> implements
		AdminNewsService {
    @Resource
    @Override
    public void setDao(AdminNewsDao newsDao) {
        this.dao = newsDao;
    }
	/**
	 * @param e
	 */
	public List<News> selecIndexNews(News e) {
		return dao.selecIndexNews(e);
	}

	@Override
	public void updateStatus(String[] ids, String status) {
		if(ids==null || ids.length==0){
			return;
		}
		
		for(int i=0;i<ids.length;i++){
			News news = new News();
			news.setId(ids[i]);
			news.setStatus(status);
			dao.sync(news);
		}
//		throw new NullPointerException();
	}

	@Override
	public void updateDownOrUp(News news) {
		dao.updateDownOrUp(news);
	}

	@Override
	public int selectCount(News news) {
		return dao.selectCount(news);
	}

}
