package com.zlkj.ssm.shop.web.controller;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
@Controller
public class FreemarkerControllerTest {
	@RequestMapping(value={"/loadTag"},method=RequestMethod.GET)
	public String  loadTag(Model model, HttpServletRequest request,
			HttpServletResponse response, ModelAndView modelAndView,
			Map<String, Object> map, ModelMap modelMap,String key){
		
			return "demo";
	}
	@RequestMapping(value={"/i18n"},method=RequestMethod.GET)
	public void   i18n(Model model, HttpServletRequest request,
			HttpServletResponse response, ModelAndView modelAndView,
			Map<String, Object> map, ModelMap modelMap,String key){
			System.out.println(RequestContextUtils.findWebApplicationContext(request, request.getServletContext()).getMessage("order_status_init", null, "Default",null));
	}
}
