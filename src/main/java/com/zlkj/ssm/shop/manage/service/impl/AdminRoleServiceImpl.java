package com.zlkj.ssm.shop.manage.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zlkj.ssm.shop.core.ServersManager;
import com.zlkj.ssm.shop.core.Services;
import com.zlkj.ssm.shop.core.entity.Privilege;
import com.zlkj.ssm.shop.core.entity.Role;
import com.zlkj.ssm.shop.core.page.PagerModel;
import com.zlkj.ssm.shop.manage.dao.AdminRoleDao;
/**
 * 角色业务逻辑实现类
 */
@Transactional
@Service
public class AdminRoleServiceImpl extends ServersManager<Role, AdminRoleDao>implements Services<Role> {
    @Resource
	private AdminPrivilegeServiceImpl privilegeService;
    @Resource
	public void setDao(AdminRoleDao dao) {
		this.dao = dao;
	}

	public PagerModel selectPageList(Role role) {
		return super.selectPageList(role);
	}

	public void setPrivilegeService(AdminPrivilegeServiceImpl privilegeService) {
		this.privilegeService = privilegeService;
	}

	/**
	 * 删除指定角色以及该角色下的所有权限
	 * 
	 * @param role
	 */
	public int delete(Role role) {
		// 删除角色
		dao.delete(role);
		// 删除角色对应的权限
		privilegeService.deleteByRole(role);
		return 0;
	}

	/**
	 * 编辑角色
	 * 
	 * @param role
	 * @throws Exception
	 */
	public void editRole(Role role, String insertOrUpdate) throws Exception {
		int insertRole = 0;
		Privilege privilege = new Privilege();
		if (insertOrUpdate.equals("1")) {
			// 新增角色
			insertRole = insert(role);
		} else {
			// 修改角色
			insertRole = update(role);
			// 删除角色的所有权限
			privilege.setRid(String.valueOf(insertRole));
			privilegeService.delete(privilege);
		}

		// 赋予权限
		if (role.getPrivileges() == null
				|| role.getPrivileges().trim().equals(""))
			return;

		String[] pArr = role.getPrivileges().split(",");
		for (int i = 0; i < pArr.length; i++) {
			privilege.clear();

			privilege.setMid(pArr[i]);
			privilege.setRid(String.valueOf(insertRole));
			privilegeService.insert(privilege);
		}
	}

	/**
	 * 批量删除角色
	 * 
	 * @param ids
	 */
	public int deletes(String[] ids) {
		Role role = new Role();
		for (int i = 0; i < ids.length; i++) {
			role.setId(ids[i]);
			delete(role);
			role.clear();
		}
		return 0;
	}

}
