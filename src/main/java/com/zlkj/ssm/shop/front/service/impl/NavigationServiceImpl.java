package com.zlkj.ssm.shop.front.service.impl;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import com.zlkj.ssm.shop.core.ServersManager;import com.zlkj.ssm.shop.front.dao.NavigationDao;import com.zlkj.ssm.shop.front.entity.Navigation;import com.zlkj.ssm.shop.front.service.NavigationService;@Transactional@Servicepublic class NavigationServiceImpl extends ServersManager<Navigation, NavigationDao> implements		NavigationService {    @Autowired    @Override    public void setDao(NavigationDao navigationDao) {        this.dao = navigationDao;    }}