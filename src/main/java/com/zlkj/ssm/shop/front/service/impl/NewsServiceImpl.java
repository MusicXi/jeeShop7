package com.zlkj.ssm.shop.front.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlkj.ssm.shop.core.ServersManager;
import com.zlkj.ssm.shop.front.dao.NewsDao;
import com.zlkj.ssm.shop.front.entity.News;
import com.zlkj.ssm.shop.front.service.NewsService;
@Transactional
@Service
public class NewsServiceImpl extends ServersManager<News, NewsDao> implements
		NewsService {
    @Autowired
    @Override
    public void setDao(NewsDao newsDao) {
        this.dao = newsDao;
    }
	/**
	 * @param e
	 */
	public List<News> selecIndexNews(News e) {
		return dao.selecIndexNews(e);
	}

	@Override
	public List<String> selectAllMd5() {
		// TODO Auto-generated method stub
		return dao.selectAllMd5();
	}

	@Override
	public void updateInBlackList(String[] ids) {
		// TODO Auto-generated method stub
//		if(ids==null || ids.length==0){
//			return;
//		}
//		
//		for(int i=0;i<ids.length;i++){
//			String e = ids[i];
//			newsDao.updateInBlackList(e);
//		}
	}

	@Override
	public void sync(String[] ids, int status) {
//		if(ids==null || ids.length==0){
//			return;
//		}
//		
//		for(int i=0;i<ids.length;i++){
//			News news = new News();
//			news.setId(ids[i]);
////			news.setStatus(status);
//			newsDao.sync(news);
//		}
	}

	@Override
	public List<News> selectNoticeList(News news) {
		return dao.selectNoticeList(news);
	}

	@Override
	public News selectSimpleOne(News news) {
		return dao.selectSimpleOne(news);
	}

}
