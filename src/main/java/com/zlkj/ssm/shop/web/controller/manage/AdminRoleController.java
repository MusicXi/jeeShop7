package com.zlkj.ssm.shop.web.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zlkj.ssm.shop.core.ManageContainer;
import com.zlkj.ssm.shop.core.Services;
import com.zlkj.ssm.shop.core.entity.Role;
import com.zlkj.ssm.shop.core.entity.User;
import com.zlkj.ssm.shop.core.page.PagerModel;
import com.zlkj.ssm.shop.exception.NotThisMethod;
import com.zlkj.ssm.shop.manage.service.impl.AdminMenuServiceImpl;
import com.zlkj.ssm.shop.manage.service.impl.AdminRoleServiceImpl;
import com.zlkj.ssm.shop.web.controller.BaseController;
import com.zlkj.ssm.shop.web.tools.LoginUserHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * 角色
 */
@Controller
@RequestMapping("/manage/role")
public class AdminRoleController extends BaseController<Role> {
    @Autowired
	private AdminRoleServiceImpl roleService;
    @Autowired
	private AdminMenuServiceImpl menuService;

	public void setRoleService(AdminRoleServiceImpl roleService) {
		this.roleService = roleService;
	}

	public void setMenuService(AdminMenuServiceImpl menuService) {
		this.menuService = menuService;
	}

    public AdminRoleController() {
        super.page_toList = "/manage/system/role/roleList";
        super.page_toEdit = "/manage/system/role/editRole";
        super.page_toAdd = "/manage/system/role/editRole";
    }
	/**
	 * 添加角色
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
	public String save(HttpServletRequest request, Role role) throws Exception {
		role.setRole_name(request.getParameter("roleName"));
        role.setId(request.getParameter("id"));
        role.setRole_desc(request.getParameter("roleDesc"));
        role.setRole_dbPrivilege(request.getParameter("role_dbPrivilege"));
        role.setPrivileges(request.getParameter("privileges"));
        role.setStatus(request.getParameter("status"));
		if(role.getRole_name()==null || role.getRole_name().trim().equals("")){
			return "0";
		}else{
			roleService.editRole(role, request.getParameter("insertOrUpdate"));
		}
		
		return "1";
	}
	
	@Override
    @RequestMapping(value = "deletes", method = RequestMethod.POST)
	public String deletes(HttpServletRequest request, String[] ids, @ModelAttribute("e") Role e, RedirectAttributes flushAttrs) throws Exception {
		throw new NotThisMethod(ManageContainer.not_this_method);
	}

	/**
	 * 批量删除角色和角色下的所有权限
	 * @return
	 * @throws Exception
	 */
//	public String delet() throws Exception {
//		logger.error("role.delete...");
//		throw new NotThisMethod(ManageContainer.not_this_method);
////		if(getIds()!=null && getIds().length>0){
////			for(int i=0;i<getIds().length;i++){
////				if(getIds()[i].equals("1")){
////					throw new Exception("超级管理员不可删除！");
////				}
////			}
////			roleService.deletes(getIds());
////		}
////		return selectList();
//	}



	@Override
	public Services<Role> getService() {
		return this.roleService;
	}

	@Override
	public void insertAfter(Role e) {
		e.clear();
	}
	@Override
	protected void selectListAfter(PagerModel pager) {
		pager.setPagerUrl("selectList");
	}
	
	/**
	 * 只能是admin才具有编辑其他用户权限的功能
	 */
	@Override
    @RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(HttpServletRequest request, @ModelAttribute("e") Role role, RedirectAttributes flushAttrs) throws Exception {
        User user = LoginUserHolder.getLoginUser();
		if(!user.getUsername().equals("admin")){
			throw new NullPointerException(ManageContainer.RoleAction_update_error);
		}
		return super.update(request, role, flushAttrs);
	}
}
