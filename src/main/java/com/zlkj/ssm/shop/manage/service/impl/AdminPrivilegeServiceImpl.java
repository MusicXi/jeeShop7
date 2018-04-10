package com.zlkj.ssm.shop.manage.service.impl;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zlkj.ssm.shop.core.ServersManager;
import com.zlkj.ssm.shop.core.Services;
import com.zlkj.ssm.shop.core.entity.Privilege;
import com.zlkj.ssm.shop.core.entity.Role;
import com.zlkj.ssm.shop.manage.dao.AdminPrivilegeDao;
import javax.annotation.Resource;

/**
 * 权限业务逻辑实现类
 * 
 * @author huangf
 * 
 */
@Transactional
@Service
public class AdminPrivilegeServiceImpl extends ServersManager<Privilege, AdminPrivilegeDao> implements Services<Privilege> {
    @Resource
	public void setDao(AdminPrivilegeDao adminPrivilegeDao) {
		this.dao = adminPrivilegeDao;
	}

	public List<Privilege> selectList(Privilege privilege) {
	/*	if (privilege == null)
			return dao.selectList("privilege.selectList");
		return dao.selectList("privilege.selectList", privilege);
	*/
		return dao.selectList(privilege);
	}

	public Privilege selectOne(Privilege privilege) {
		return (Privilege) dao.selectOne(privilege);
	}

	public int insert(Privilege privilege) {
		return dao.insert(privilege);
	}

	public int delete(Privilege privilege) {
		return dao.delete(privilege);
	}

	public int update(Privilege privilege) {
		return dao.update(privilege);
	}

	/**
	 * 根绝角色删除权限
	 * 
	 * @param role
	 */
	public void deleteByRole(Role role) {
		Privilege privilege = new Privilege();
		privilege.setRid(role.getId());
		delete(privilege);
	}

}
