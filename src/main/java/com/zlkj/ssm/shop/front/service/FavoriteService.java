package com.zlkj.ssm.shop.front.service;import com.zlkj.ssm.shop.core.Services;import com.zlkj.ssm.shop.front.entity.Favorite;public interface FavoriteService extends Services<Favorite> {	int selectCount(Favorite favorite);}