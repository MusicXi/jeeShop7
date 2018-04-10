package com.zlkj.ssm.shop.web.controller;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;
import com.zlkj.ssm.shop.common.tools.SerializeUtils;
import com.zlkj.ssm.shop.core.cache.provider.RedisCacheProvider;
import com.zlkj.ssm.shop.front.entity.Area;
import com.zlkj.ssm.shop.front.service.AreaService;
@Controller
public class RedisCacheControllerTest {
	@Autowired private AreaService areaService;
	@Autowired private RedisCacheProvider redisProvider;
	@RequestMapping(value={"/loadArea"},method=RequestMethod.GET)
	@ResponseBody
	public Object loadAreas(Model model, HttpServletRequest request,
			HttpServletResponse response, ModelAndView modelAndView,
			Map<String, Object> map, ModelMap modelMap,String key){
		return JSONObject.toJSON(loadArea(model, request, response,
				modelAndView, map, modelMap, request.getRequestURL()+"loadArea"));
	}
	@SuppressWarnings("unchecked")
	public List<Area>   loadArea(Model model, HttpServletRequest request,
			HttpServletResponse response, ModelAndView modelAndView,
			Map<String, Object> map, ModelMap modelMap,String key){
			try {
				List<Area> areasList=null;
				areasList =(List<Area>) SerializeUtils.unSerialize(
						SerializeUtils.serialize(redisProvider.get(key)));
				if(null==areasList){
					areasList = areaService.selectList(new Area());
					if(null==areasList) return null;
					redisProvider.put(key,(Serializable) areasList);
				}
				return areasList;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}
	
	@GetMapping(value="/demo")
	public String  loadDemo(Model model, HttpServletRequest request,
			HttpServletResponse response, ModelAndView modelAndView,
			Map<String, Object> map, ModelMap modelMap){
			return "demo";
	}
	@RequestMapping(value="jsonDate",method=RequestMethod.POST)
	@ResponseBody
	public String returnJson(Model model, HttpServletRequest request,
			HttpServletResponse response, ModelAndView modelAndView,
			Map<String, Object> map, ModelMap modelMap)throws Exception{
			return "{\"errcode\":40004,\"errmsg\":\"invalid media type\"}";
	}
}
