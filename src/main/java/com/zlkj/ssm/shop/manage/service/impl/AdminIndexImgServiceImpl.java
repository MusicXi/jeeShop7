package com.zlkj.ssm.shop.manage.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlkj.ssm.shop.core.ServersManager;
import com.zlkj.ssm.shop.manage.dao.AdminIndexImgDao;
import com.zlkj.ssm.shop.manage.entity.IndexImg;
import com.zlkj.ssm.shop.manage.service.AdminIndexImgService;

import javax.annotation.Resource;
@Transactional
@Service
public class AdminIndexImgServiceImpl extends ServersManager<IndexImg, AdminIndexImgDao> implements
		AdminIndexImgService {

    @Resource
    @Override
    public void setDao(AdminIndexImgDao indexImgDao) {
        this.dao = indexImgDao;
    }

	@Override
	public List<IndexImg> getImgsShowToIndex(int i) {
		return dao.getImgsShowToIndex(i);
	}

}
