package com.zlkj.ssm.shop.manage.service.impl;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zlkj.ssm.shop.core.ServersManager;
import com.zlkj.ssm.shop.core.entity.User;
import com.zlkj.ssm.shop.core.page.PagerModel;
import com.zlkj.ssm.shop.manage.dao.AdminUserDao;
import com.zlkj.ssm.shop.manage.service.AdminUserService;

import javax.annotation.Resource;
/**
 * 用户业务逻辑实现类
 */
@Transactional
@Service
public class AdminUserServiceImpl extends ServersManager<User, AdminUserDao> implements AdminUserService {
    @Resource
	public void setDao(AdminUserDao dao) {
		this.dao = dao;
	}

	public User login(User user) {
//		user.setStatus(User.user_status_y);
		return (User) dao.selectOne(user);
	}

	public List<User> selectList(User user) {
		//if (user == null)
			//return dao.selectList("user.selectList");
		return dao.selectList(user);
	}


	/**
	 * 批量删除用户
	 * 
	 * @param ids
	 */
	public int deletes(String[] ids) {
		User user = new User();
		for (int i = 0; i < ids.length; i++) {
			user.setId(ids[i]);
			delete(user);
		}
		return 0;
	}


	public PagerModel selectPageList(User e) {
		return super.selectPageList( e);
	}


	@Override
	public User selectById(String id) {
		User user = new User();
		user.setId(id);
		return selectOne(user);
	}
	
	/**
	 * 根据条件查询数量
	 * @param user
	 * @return
	 */
	public int selectCount(User user) {
		if(user==null){
			throw new NullPointerException();
		}
		return (Integer) dao.selectCount(user);
	}

	public User selectOneByCondition(User user) {
		if(user==null){
			throw new NullPointerException();
		}
		return (User) dao.selectOneByCondition( user);
	}

}
