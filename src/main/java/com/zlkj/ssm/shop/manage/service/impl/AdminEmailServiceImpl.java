package com.zlkj.ssm.shop.manage.service.impl;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import com.zlkj.ssm.shop.core.ServersManager;import com.zlkj.ssm.shop.manage.dao.AdminEmailDao;import com.zlkj.ssm.shop.manage.entity.Email;import com.zlkj.ssm.shop.manage.service.AdminEmailService;import javax.annotation.Resource;@Transactional@Servicepublic class AdminEmailServiceImpl extends ServersManager<Email,AdminEmailDao> implements		AdminEmailService {    @Resource    @Override    public void setDao(AdminEmailDao emailDao) {        this.dao = emailDao;    }}