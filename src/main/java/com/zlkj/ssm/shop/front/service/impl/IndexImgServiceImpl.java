package com.zlkj.ssm.shop.front.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlkj.ssm.shop.core.ServersManager;
import com.zlkj.ssm.shop.front.dao.IndexImgDao;
import com.zlkj.ssm.shop.front.entity.IndexImg;
import com.zlkj.ssm.shop.front.service.IndexImgService;

import javax.annotation.Resource;
@Transactional
@Service
public class IndexImgServiceImpl extends ServersManager<IndexImg, IndexImgDao> implements
		IndexImgService {

    @Resource
    @Override
    public void setDao(IndexImgDao indexImgDao) {
        this.dao = indexImgDao;
    }

	@Override
	public List<IndexImg> getImgsShowToIndex(int i) {
		return dao.getImgsShowToIndex(i);
	}

}
