package com.zlkj.ssm.shop.manage.service.impl;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import com.zlkj.ssm.shop.core.ServersManager;import com.zlkj.ssm.shop.manage.dao.AdminCommentTypeDao;import com.zlkj.ssm.shop.manage.entity.CommentType;import com.zlkj.ssm.shop.manage.service.AdminCommentTypeService;import javax.annotation.Resource;@Transactional@Servicepublic class AdminCommentTypeServiceImpl extends ServersManager<CommentType, AdminCommentTypeDao>		implements AdminCommentTypeService {    @Resource    @Override    public void setDao(AdminCommentTypeDao commentTypeDao) {        this.dao = commentTypeDao;    }    @Override	public int update(CommentType e) {		dao.updateAllN();		super.update(e);		return 1;	}}