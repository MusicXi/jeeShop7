package com.zlkj.ssm.shop.web.controller.manage;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Controller;import org.springframework.ui.ModelMap;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.servlet.mvc.support.RedirectAttributes;import com.zlkj.ssm.shop.manage.entity.Advert;import com.zlkj.ssm.shop.manage.service.AdminAdvertService;import com.zlkj.ssm.shop.web.controller.BaseController;import javax.servlet.http.HttpServletRequest;/** * 广告管理 * */@Controller@RequestMapping("/manage/advert/")public class AdminAdvertController extends BaseController<Advert> {	private static final Logger logger = LoggerFactory.getLogger(AdminAdvertController.class);	private static final String page_toList = "/manage/advert/advertList";	private static final String page_toEdit = "/manage/advert/advertEdit";	private static final String page_toAdd = "/manage/advert/advertEdit";	private AdminAdvertController() {		super.page_toList = page_toList;		super.page_toAdd = page_toAdd;		super.page_toEdit = page_toEdit;	}	@Autowired	private AdminAdvertService advertService;	@Override	public AdminAdvertService getService() {		return advertService;	}	public void setAdvertService(AdminAdvertService advertService) {		this.advertService = advertService;	}	@Override	public String insert(HttpServletRequest request, Advert e, RedirectAttributes flushAttrs) throws Exception {		logger.info(">>>AdvertAction.insert");		super.insert(request, e, flushAttrs);		String id = e.getId();		logger.info(">>>AdvertAction.insert ,id = " + e.getId());		e = getService().selectById(id);		return "redirect:toEdit2?id="+e.getId();	}		@Override	public String update(HttpServletRequest request, Advert e, RedirectAttributes flushAttrs) throws Exception {		String id = e.getId();		super.update(request, e, flushAttrs);		e = getService().selectById(id);		return "redirect:toEdit2?id="+e.getId();	}		/**	 * 编辑	 */	@RequestMapping("toEdit2")	public String toEdit2(Advert advert, ModelMap model) throws Exception {		return toEdit0(advert, model);	}		private String toEdit0(Advert advert, ModelMap model) throws Exception {		return super.toEdit(advert, model);	}	}