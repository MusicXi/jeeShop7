package com.zlkj.ssm.shop.web.controller.manage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zlkj.ssm.shop.web.freemarker.core.FreemarkerHelper;
import com.zlkj.ssm.shop.web.tools.RequestHolder;

/**
 * 网站会员
 */
@Controller
@RequestMapping("freemarker")
public class AdminFreemarkerController {
	private static final Logger logger = LoggerFactory.getLogger(AdminFreemarkerController.class);
	@Autowired
	private FreemarkerHelper freemarkerHelper;

	public FreemarkerHelper getFreemarkerHelper() {
		return freemarkerHelper;
	}

	public void setFreemarkerHelper(FreemarkerHelper freemarkerHelper) {
		this.freemarkerHelper = freemarkerHelper;
	}

	@RequestMapping("toIndex")
	public String toIndex() {
		return "/manage/freemarker/freemarkerList";
	}

	/**
	 * 生成门户菜单
	 * 
	 * @return
	 * @throws java.io.IOException
	 */
//	@Deprecated
//	public String indexMenu() {
//		freemarkerHelper.index("indexMenu.ftl", "indexMenu.jsp");
//		return SUCCESS;
//	}
	
	/**
	 * 对商品的详情内容、帮助文章的内容、公告、新闻等的内容进行静态化处理。
	 * 方法是将这些大文本的内容存储未jsp的文件格式，将来访问页面的时候直接把地址链接进去就行了。
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("create")
	@ResponseBody
	public String create() throws Exception{
		String method = RequestHolder.getRequest().getParameter("method");
		logger.error("create method = " + method);
		if(StringUtils.isBlank(method)){
			
		}else if(method.equals("helps")){
			freemarkerHelper.helps();//所有帮助文件静态化
		}else if(method.equals("notices")){
			freemarkerHelper.notices();//所有公告通知静态化
		}else if(method.equals("products")){
			String error = freemarkerHelper.products();//所有商品描述静态化
			if(error==null){
				return ("success");
			}else{
				return ("部分商品静态化失败，商品ID："+error);
			}
		}else if(method.equals("staticProductByID")){
			String id = RequestHolder.getRequest().getParameter("id");
			
			String response = freemarkerHelper.staticProductByID(id);//所有商品描述静态化
			return (response);
		}else if(method.equals("staticNewsByID")){
			String id = RequestHolder.getRequest().getParameter("id");
			
			String response = freemarkerHelper.staticNewsByID(id);//所有商品描述静态化
			return (response);
		}
		
		return ("success");
//		return SUCCESS;
	}
	
//	public String helps(){
//		String method = getRequest().getParameter("method");
//		if(StringUtils.isBlank(method)){
//			
//		}else if(method.equals("staticNews")){
//			//生成所有的文章静态页面
//			freemarkerHelper.staticNews();
//		}else if(method.equals("staticNews")){
//			//对单个文章静态化
//			String id = getRequest().getParameter("id");
//			freemarkerHelper.staticNewsInfo(id);
//		}
//		return SUCCESS;
//	}

	/**
	 * 一键生成
	 * 
	 * @return
	 */
//	@Deprecated
//	public String createAll() {
//		indexMenu();
//		return SUCCESS;
//	}
}
