package com.zlkj.ssm.shop.front.dao;import com.zlkj.ssm.shop.core.DaoManager;import com.zlkj.ssm.shop.front.entity.Orderdetail;public interface OrderdetailDao extends DaoManager<Orderdetail> {	int selectCount(String orderID);}