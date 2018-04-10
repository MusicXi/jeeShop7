package com.zlkj.ssm.shop.web.controller.front;import java.io.IOException;import java.text.SimpleDateFormat;import java.util.ArrayList;import java.util.Date;import java.util.Iterator;import java.util.LinkedHashMap;import java.util.LinkedList;import java.util.List;import java.util.Map;import javax.servlet.http.HttpServletRequest;import org.apache.commons.lang.StringUtils;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Controller;import org.springframework.ui.ModelMap;import org.springframework.web.bind.annotation.ModelAttribute;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RequestMethod;import org.springframework.web.bind.annotation.ResponseBody;import org.springframework.web.servlet.mvc.support.RedirectAttributes;import com.alibaba.fastjson.JSON;import com.zlkj.ssm.shop.common.tools.AddressUtils;import com.zlkj.ssm.shop.common.tools.DateTimeUtil;import com.zlkj.ssm.shop.common.tools.MD5;import com.zlkj.ssm.shop.core.FrontContainer;import com.zlkj.ssm.shop.core.cache.provider.SystemManager;import com.zlkj.ssm.shop.core.page.PagerModel;import com.zlkj.ssm.shop.enums.LoginTypeEnum;import com.zlkj.ssm.shop.front.entity.Account;import com.zlkj.ssm.shop.front.entity.Address;import com.zlkj.ssm.shop.front.entity.Area;import com.zlkj.ssm.shop.front.entity.CartInfo;import com.zlkj.ssm.shop.front.entity.Email;import com.zlkj.ssm.shop.front.entity.Favorite;import com.zlkj.ssm.shop.front.entity.NotifyTemplate;import com.zlkj.ssm.shop.front.entity.Order;import com.zlkj.ssm.shop.front.entity.OrderSimpleReport;import com.zlkj.ssm.shop.front.entity.Product;import com.zlkj.ssm.shop.front.service.AccountService;import com.zlkj.ssm.shop.front.service.AddressService;import com.zlkj.ssm.shop.front.service.EmailService;import com.zlkj.ssm.shop.front.service.FavoriteService;import com.zlkj.ssm.shop.front.service.OrderService;import com.zlkj.ssm.shop.front.service.ProductService;import com.zlkj.ssm.shop.manage.service.AdminNewsService;import com.zlkj.ssm.shop.web.controller.BaseController;import com.zlkj.ssm.shop.web.tools.LoginUserHolder;import com.zlkj.ssm.shop.web.tools.RequestHolder;/** * 门户会员服务类 *  * @author huangf *  */@Controller("frontAccountController")@RequestMapping("account")public class AccountAction extends BaseController<Account> {	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AccountAction.class);	@Autowired	private AccountService accountService;	@Autowired	private OrderService orderService;	@Autowired	private AdminNewsService newsService;	@Autowired	private AddressService addressService;//配送地址service//	private List<Address> addressList;//配送地址列表	@Autowired	private ProductService productService;	@Autowired	private FavoriteService favoriteService;//商品收藏夹//	private String selectLeftMenu;//选中的个人中心的菜单项	@Autowired	private EmailService emailService;	//	private String helpCode;//帮助code//	private News news;//文章//	private Address address;//配送地址//	private OrderSimpleReport orderSimpleReport;//简单报表		private static final Object qq_login_lock = new Object();//qq登陆，本地锁	private static final Object sinawb_login_lock = new Object();//新浪微博登陆，本地锁		// 登陆错误信息	private static final String toLogin = "/account/login";//转到登陆界面,forword方式 地址不变	private static final String toLoginRedirect = "redirect:/account/login";//转到登陆界面,getResponse().sendRedirect(arg0)方式 地址变化	private static final String toIndex = "redirect:/";//转到门户首页	@Override	public AccountService getService() {		return accountService;	}	public void setAccountService(AccountService accountService) {		this.accountService = accountService;	}	public void prepare(Account e) throws Exception {		logger.error("AccountAction.prepare...");//		if (e == null) {//			e = new Account();//		}else{//			e.clear();//		}////		if(address==null){//			this.address = new Address();//		}else{//			address.clear();//		}////		errorMsg = null;////		if(orderSimpleReport!=null){//			orderSimpleReport.clear();//			orderSimpleReport = null;//		}////		/**//		 * 清除地址列表数据//		 *///		if(addressList!=null && addressList.size()>0){//			for(int i=0;i<addressList.size();i++){//				addressList.get(i).clear();//			}//			addressList.clear();//			addressList = null;//		}////		super.setSelectMenu(FrontContainer.not_select_menu);//设置主菜单为不选中	}	/**	 * 用户注册	 * 	 * @return	 * @throws IOException 	 */	@RequestMapping(value = "doRegister", method = RequestMethod.POST)	public String doRegister(Account e, ModelMap model) throws IOException {		if(StringUtils.isBlank(e.getEmail())){			throw new NullPointerException("邮箱不能为空！");		}				e.setPassword(MD5.md5(e.getPassword()));		if (StringUtils.isBlank(e.getId())) {			// 用户注册			getService().insert(e);						//发送邮件到用户的邮箱//			MailUtil mail = new MailUtil(e.getEmail());//new MailUtil("543089122@qq.com",SystemManager.getInstance().get("from_email_account"),SystemManager.getInstance().get("from_email_password"), SystemManager.getInstance().get("from_eamil_smtpServer"), "myshop注册验证邮件");//用户注册成功发送邮件//			boolean result = mail.startSend("注册邮箱验证","恭喜，您在myshop站点的账号注册成功！点击下面的链接进行验证。有效时间为2小时。http://myshop.itelse.com");//			logger.error("email resule = " + result);						accountService.sendEmail(e, NotifyTemplate.email_reg);		} else {			// 修改密码//			getServer().update(e);			throw new NullPointerException("不支持！");		}		//		getSession().setAttribute("checkEmail","checkEmail");		model.addAttribute("uid", e.getId());//		getResponse().sendRedirect(SystemManager.systemSetting.getWww()+"/account/checkEmail.html");		return "redirect:/account/checkEmail.html";	}		/**	 * 用户注册--》再次发送邮件	 * @return	 * @throws IOException 	 */	@RequestMapping(value = "sendEmailAgain", method = RequestMethod.POST)	public String sendEmailAgain(String uid, ModelMap model) throws IOException{		if(StringUtils.isBlank(uid)){			throw new NullPointerException("参数不正确！");		}				Account acc = accountService.selectById(uid);		if(acc==null){			throw new NullPointerException("根据用户ID查询不到用户信息！");		}				accountService.sendEmail(acc, NotifyTemplate.email_reg);		model.addAttribute("uid", acc.getId());//		getResponse().sendRedirect(SystemManager.systemSetting.getWww()+"/account/checkEmail.html");		return "redirect:/account/checkEmail.html";	}	/**	 * 转到邮箱验证提示页面	 * @return	 */	@RequestMapping("checkEmail")	public String checkEmail(){		logger.info("checkEmail");		//		Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);//		if (acc == null || StringUtils.isBlank(acc.getAccount())) {//			return toLogin;//		}		return "/account/regsuccess";	}		/**	 * 转到忘记密码页面	 * @return	 */	@RequestMapping("forget")	public String forget(){		return "/account/forget";	}		/**	 * 找回密码	 * @return	 * @throws Exception 	 */	@RequestMapping(value = "doForget", method = RequestMethod.POST)	public String doForget(Account e, ModelMap model) throws Exception{//		synchronized (this) {//			String token = getRequest().getParameter("token");//			logger.error("doForget...token="+token);//			boolean _isTokenValid = TokenUtil.getInstance().isTokenValid(getRequest());//			logger.error("_isTokenValid = " + _isTokenValid);//			if(!TokenUtil.getInstance().isTokenValid(getRequest())){//				throw new Exception("表单重复提交了！");//			}//		}//		String account = getRequest().getParameter("account");//		if(StringUtils.isNotBlank(account)){//			//如果此值不为空，则说明是 重新发送按钮 请求的此方法。重新发送按钮来重发邮件//			e.setAccount(account);//		}				accountService.doForget(e);		//等待用户检查短信或邮件		return "redirect:/account/waitUserCheck.html";	}	@RequestMapping("waitUserCheck")	public String waitUserCheck(){		return "/account/waitUserCheck";	}		/**	 * ajax检查用户名称是否存在	 * @return	 * @throws IOException 	 */	@RequestMapping("checkAccountExist")	@ResponseBody	public String checkAccountExist(Account e) throws IOException{		if(StringUtils.isBlank(e.getAccount())){			return ("{\"error\":\"用户名不能为空!\"}");		}else{			Account acc = new Account();			acc.setAccount(e.getAccount());			if(accountService.selectCount(acc)==0){				return ("{\"error\":\"用户名不存在!\"}");			}else{				return ("{\"ok\":\"用户名输入正确!\"}");			}		}	}	/**	 * ajax检查密码是否正确	 * @return	 * @throws IOException 	 */	@RequestMapping("checkPassword")	@ResponseBody	public String checkPassword(Account e) throws IOException{		if(StringUtils.isBlank(e.getPassword())){			return ("{\"error\":\"密码不能为空!\"}");		}else{			Account acc = new Account();			acc.setPassword(MD5.md5(e.getPassword()));			if(accountService.selectCount(acc)==0){				return ("{\"error\":\"输入的密码不正确!\"}");			}else{				return ("{\"ok\":\"密码正确!\"}");			}		}	}		/**	 * ajax检查新邮箱不能和原邮箱一致	 * @return	 * @throws IOException 	 */	@RequestMapping("changeEmailCheck")	@ResponseBody	public String changeEmailCheck(Account e) throws IOException{		if(StringUtils.isBlank(e.getNewEmail())){			return ("{\"error\":\"新邮箱不能为空!\"}");		}else{			Account acc = (Account) RequestHolder.getSession().getAttribute(FrontContainer.USER_INFO);			if (acc == null || StringUtils.isBlank(acc.getAccount())) {				return toLogin;			}						if(acc.getEmail().equals(e.getNewEmail())){				return ("{\"error\":\"新邮箱不能和原邮箱一致!\"}");			}else{				return ("{\"ok\":\"系统认为此邮箱可用!\"}");			}		}	}		/**	 * 转到登陆页面	 * @return	 */	@RequestMapping("login")	public String login() {		logger.error("toLogin...");		if (LoginUserHolder.getLoginAccount() != null) {			return toIndex;		}		return toLogin;	}		/**	 * 转到注册页面	 * @return	 */	@RequestMapping("register")	public String register() {		logger.error("register...");		if (LoginUserHolder.getLoginAccount() != null) {			return toIndex;		}		return "/account/register";	}		/**	 * 用户登陆	 * 	 * @return	 */	@RequestMapping("doLogin")	public String doLogin(Account e, ModelMap model, RedirectAttributes flushAttrs) {		logger.error("doLogin()...");		if (LoginUserHolder.getLoginAccount() != null) {			return toIndex;		}		String errorMsg = "<font color='red'>帐号或密码错误!</font>";		if (e.getAccount() == null || e.getAccount().trim().equals("")				|| e.getPassword() == null || e.getPassword().trim().equals("")){			model.addAttribute("errorMsg", errorMsg);			logger.error("doLogin.errorMsg="+errorMsg);			return toLogin;		}		//用户验证		e.setPassword(MD5.md5(e.getPassword()));		String account = e.getAccount();		String password = e.getPassword();		e.clear();		e.setAccount(account);		e.setPassword(password);		Account acc = accountService.selectOne(e);		if (acc == null) {			model.addAttribute("errorMsg", errorMsg);			return toLogin;		}else if(acc.getFreeze().equals(Account.account_freeze_y)){			if(StringUtils.isBlank(acc.getFreezeStartdate()) && StringUtils.isBlank(acc.getFreezeEnddate())){				model.addAttribute("errorMsg", "<font color='red'>此账号已永久冻结!有疑问请联系站点管理员!</font>");			}else{				model.addAttribute("errorMsg", "<font color='red'>此账号已暂时冻结!有疑问请联系站点管理员!</font>");			}			return toLogin;		}else if(acc.getEmailIsActive().equals(Account.account_emailIsActive_n)){			//邮箱未激活			errorMsg = "<font color='red'>此账号的邮箱尚未激活，请立即去激活邮箱！</font>";			model.addAttribute("errorMsg", errorMsg);			return toLogin;		}		errorMsg = null;		acc.setLoginType(LoginTypeEnum.system);//登陆方式		RequestHolder.getSession().setAttribute(FrontContainer.USER_INFO, acc);				//更新用户最后登录时间		e.clear();		e.setId(acc.getId());		e.setLastLoginTime("yes");		e.setLastLoginIp(AddressUtils.getIp(RequestHolder.getRequest()));		String address = null;		try {			address = AddressUtils.getAddresses("ip=" + e.getLastLoginIp(), "utf-8");		} catch (Exception ex) {			ex.printStackTrace();		}				e.setLastLoginArea(address);		accountService.update(e);		return toIndex;	}	/**	 *	 * @return	 */	@RequestMapping("exit")	public String exit() {		logout();		return "redirect:/account/login";	}	/**	 * 用户注销	 * 	 * @return	 */	@RequestMapping("logout")	public String logout() {		//清除用户session		RequestHolder.getSession().setAttribute(FrontContainer.USER_INFO, null);				//清除用户购物车缓存		CartInfo cartInfo = (CartInfo) RequestHolder.getSession().getAttribute(FrontContainer.myCart);		if(cartInfo!=null){			cartInfo.clear();		}		RequestHolder.getSession().setAttribute(FrontContainer.myCart, null);		//清除历史浏览记录		LinkedHashMap<String, Product> history_product_map = (LinkedHashMap<String, Product>) RequestHolder.getSession().getAttribute(FrontContainer.history_product_map);//		List<String> history_product_map = (List<String>) getSession().getAttribute(FrontContainer.history_product_map);		if(history_product_map!=null){			history_product_map.clear();		}		RequestHolder.getSession().setAttribute(FrontContainer.history_product_map, null);		return toLogin;	}		/**	 * 分页查询商品收藏夹	 * @return	 * @throws Exception 	 */	@RequestMapping("favorite")	public String favorite(ModelMap model) throws Exception{		Account acc = (Account) LoginUserHolder.getLoginAccount();		if (acc == null || StringUtils.isBlank(acc.getAccount())) {			return toLogin;		}				Favorite favorite = new Favorite();		favorite.setAccount(acc.getAccount());		PagerModel pager = selectPagerFavoriteList(favorite);		if(pager!=null && pager.getList()!=null && pager.getList().size()>0){			List<String> productIds = new LinkedList<String>();			for(int i=0;i<pager.getList().size();i++){				Favorite ff = (Favorite)pager.getList().get(i);				productIds.add(ff.getProductID());			}						//根君商品ID集合加载商品信息：名称、价格、销量、是否上下架等			Product p = new Product();			p.setProductIds(productIds);			List<Product> productList = productService.selectProductListByIds(p);						//将查询出来的每一个商品对象挂到收藏夹对象上去			if(productList!=null && productList.size()>0){				for(int i=0;i<pager.getList().size();i++){					Favorite ff = (Favorite)pager.getList().get(i);					ff.setProduct(null);					for(int j=0;j<productList.size();j++){						Product product = productList.get(j);						if(ff.getProductID().equals(product.getId())){							ff.setProduct(product);							break;						}					}					if(ff.getProduct() == null){						//TODO 产品已经不存在					}				}			}		}		//		selectLeftMenu = FrontContainer.user_leftMenu_favorite;		model.addAttribute("pager", pager);		return "/account/favorite";	}	private PagerModel selectPagerFavoriteList(Favorite fovorite){		int offset = 0;		String pagerOffset = RequestHolder.getRequest().getParameter("pager.offset");		if (StringUtils.isNotBlank(pagerOffset)) {//			throw new NullPointerException();			offset = Integer.parseInt(pagerOffset);		}		if (offset < 0)			offset = 0;		fovorite.setOffset(offset);		PagerModel servicesPager = favoriteService.selectPageList(fovorite);		if(servicesPager==null)servicesPager = new PagerModel();		// 计算总页数		servicesPager.setPagerSize((servicesPager.getTotal() + servicesPager.getPageSize() - 1)				/ servicesPager.getPageSize());		return servicesPager;	}		/**	 * ajax验证输入的字符的唯一性	 * @return	 * @throws IOException	 */	@RequestMapping("unique")	@ResponseBody	public String unique(Account e) throws IOException{		logger.error("验证输入的字符的唯一性"+e);		logger.error(e.getNickname());		if(StringUtils.isNotBlank(e.getNickname())){//验证昵称是否被占用			logger.error("验证昵称是否被占用");			String nickname = e.getNickname();			e.clear();			e.setNickname(nickname);			if (accountService.selectCount(e)>0){				return ("{\"error\":\"昵称已经被占用!\"}");			}else{				return ("{\"ok\":\"昵称可以使用!\"}");			}		}else if(StringUtils.isNotBlank(e.getAccount())){//验证用户名是否被占用			logger.error("验证用户名是否被占用");			String account = e.getAccount();			e.clear();			e.setAccount(account);			if (accountService.selectCount(e)>0){				return ("{\"error\":\"用户名已经被占用!\"}");			}else{				return ("{\"ok\":\"用户名可以使用!\"}");			}		}else if(StringUtils.isNotBlank(e.getEmail())){//验证邮箱是否被占用			logger.error("验证邮箱是否被占用="+e.getEmail());			String email = e.getEmail();			e.clear();			e.setEmail(email);			if (accountService.selectCount(e) > 0){				return ("{\"error\":\"邮箱已经被占用!\"}");			}else{				return ("{\"ok\":\"邮箱可以使用!\"}");			}		}else if(StringUtils.isNotBlank(e.getVcode())){//验证验证码输入的是否正确			logger.error("检查验证码输入的是否正确"+e.getVcode());			String validateCode = RequestHolder.getSession().getAttribute(FrontContainer.validateCode).toString();			logger.error("validateCode=" + validateCode);			if(validateCode.equalsIgnoreCase(e.getVcode())){				return ("{\"ok\":\"验证码输入正确!\"}");			}else{				return ("{\"error\":\"验证码输入有误!\"}");			}//			vcode = null;		}else if(StringUtils.isNotBlank(e.getPassword())){//验证原始密码输入是否正确			logger.error("验证原始密码输入是否正确"+e.getPassword());			Account acc = LoginUserHolder.getLoginAccount();			if(StringUtils.isNotBlank(e.getPassword()) && MD5.md5(e.getPassword()).equals(acc.getPassword())){				return ("{\"ok\":\"原密码输入正确!\"}");			}else{				return ("{\"error\":\"原密码输入有误!\"}");			}		}				if(e!=null){			e.clear();		}		return null;	}		/**	 * 查看个人信息	 * @return	 */	@RequestMapping("account")	public String account(ModelMap model, Account e){		Account acc = LoginUserHolder.getLoginAccount();		if (acc == null || StringUtils.isBlank(acc.getAccount())) {			return toLogin;		}		//		selectLeftMenu = "user";		e = accountService.selectById(acc.getId());//		setSelectMenu(FrontContainer.not_select_menu);//设置主菜单为不选中		//		getSession().setAttribute(FrontContainer.WEB_USER_INFO,e);				//查询未读信件的数量//		Letters letter = new Letters();//		letter.setAccount(acc.getAccount());//		int notReadLetters = lettersService.getCount(letter);//		logger.error("notReadLetters="+notReadLetters);//		acc.setNotReadLetters(notReadLetters);		model.addAttribute("e", e);        Map<String, Area> areaMap = SystemManager.getInstance().getAreaMap();		model.addAttribute("provinces", areaMap.values());		if(StringUtils.isNotBlank(e.getProvince()) && areaMap.get(e.getProvince()) != null) {			model.addAttribute("cities", areaMap.get(e.getProvince()).getChildren());		} else {			model.addAttribute("cities", new ArrayList<Area>());		}		return "/account/account";	}		/**	 * 修改个人信息	 * @return	 */	@RequestMapping("saveSetting")	public String saveSetting(ModelMap model, Account e){		Account account = LoginUserHolder.getLoginAccount();		if (account == null || StringUtils.isBlank(account.getAccount())) {			return toLogin;		}		logger.error("saveSetting.....{}", e);//		logger.error("sex="+sex);		e.setId(account.getId());		accountService.update(e);//		Account acc = (Account) getSession().getAttribute(FrontContainer.USER_INFO);//		acc.setSign(e.getSign());//		acc.setMyself(e.getMyself());//		acc.setSex(e.getSex());				e.clear();		account = accountService.selectById(account.getId());		RequestHolder.getSession().setAttribute(FrontContainer.USER_INFO, account);//		return "saveSetting";		return account(model, e);	}		private boolean requireLogin() throws NullPointerException{		Account account = LoginUserHolder.getLoginAccount();		if (account == null || StringUtils.isBlank(account.getAccount())) {			return true;		}		return false;	}		/**	 * 配送地址管理	 * @return	 */	@RequestMapping("address")	public String address(ModelMap model, Address address){		Account account = LoginUserHolder.getLoginAccount();		if (account == null || StringUtils.isBlank(account.getAccount())) {			return toLogin;		}//		selectLeftMenu = "address";		address.setAccount(account.getAccount());		List<Address> addressList = addressService.selectList(address);		model.addAttribute("address", address);		model.addAttribute("addressList", addressList);        Map<String, Area> areaMap = SystemManager.getInstance().getAreaMap();		model.addAttribute("provinces", areaMap.values());		List<Area> cities = new ArrayList<Area>();		List<Area> areas = new ArrayList<Area>();		if(StringUtils.isNotBlank(address.getProvince()) && areaMap.get(address.getProvince()) != null) {			cities = areaMap.get(address.getProvince()).getChildren();		}		model.addAttribute("cities", cities);		String area = address.getArea();		for(Area a : cities){			if(a.getCode().equals(area)){				areas = a.getChildren();				break;			}		}		//TODO 缓存的地址只有二级，没有区县		model.addAttribute("areas", areas);		return "/account/address";	}		/**	 * 增加配送地址	 * @return	 */	@RequestMapping("saveAddress")	public String saveAddress(ModelMap model, Address address) {		Account acc = LoginUserHolder.getLoginAccount();		if (acc == null || StringUtils.isBlank(acc.getAccount())) {			return toLogin;		}//		selectLeftMenu = "address";				//需要将省市区的代号换成中文，插入到pcadetail字段里面去，显示的时候方便。		StringBuilder pcadetail = new StringBuilder();        Map<String, Area> areaMap = SystemManager.getInstance().getAreaMap();		Area sheng = areaMap.get(address.getProvince());//省		pcadetail.append(sheng.getName());				for(int i=0;i<sheng.getChildren().size();i++){			Area shi = sheng.getChildren().get(i);//市			if(shi.getCode().equals(address.getCity())){								pcadetail.append(" ").append(shi.getName());								for(int j = 0;j<shi.getChildren().size();j++){					Area qu = shi.getChildren().get(j);//区					if(qu.getCode().equals(address.getArea())){						pcadetail.append(" ").append(qu.getName());						break;					}				}								break;			}		}				address.setPcadetail(pcadetail.toString());				address.setAccount(acc.getAccount());		if(StringUtils.isBlank(address.getId())){			addressService.insert(address);		}else{			addressService.update(address);		}		address.clear();		return address(model, address);	}		/**	 * 删除指定的配送地址	 * @return	 */	@RequestMapping("deleteAddress")	public String deleteAddress(ModelMap model, Address address){		Account acc = LoginUserHolder.getLoginAccount();		if (acc == null || StringUtils.isBlank(acc.getAccount())) {			return toLogin;		}//		selectLeftMenu = "address";		String id = address.getId();		if(StringUtils.isBlank(id)){			throw new NullPointerException("id is null!");		}		Address add = new Address();		add.setId(id);		addressService.delete(add);				return address(model, address);	}	/**	 * 编辑指定的配送地址	 * @return	 */	@RequestMapping("editAddress")	public String editAddress(ModelMap model, Address address){		Account acc = LoginUserHolder.getLoginAccount();		if (acc == null || StringUtils.isBlank(acc.getAccount())) {			return toLogin;		}//		selectLeftMenu = "address";		String id = address.getId();		if(StringUtils.isBlank(id)){			throw new NullPointerException("id is null!");		}		address = addressService.selectById(id);				//获取区域列表		if(StringUtils.isNotBlank(address.getArea())){//			address.getArea()            Map<String, Area> areaMap = SystemManager.getInstance().getAreaMap();			Area area = areaMap.get(address.getProvince());			if(area!=null && area.getChildren()!=null && area.getChildren().size()>0){				for(int i=0;i<area.getChildren().size();i++){					Area city = area.getChildren().get(i);					if(city.getCode().equals(address.getCity())){												logger.error("address.getCity()="+address.getCity());						logger.error(city.toString());						address.setAreaList(city.getChildren());						break;					}				}			}		}				return address(model, address);	}		/**	 * 我的订单列表	 * @return	 * @throws Exception 	 */	@RequestMapping("orders")	public String orders(ModelMap model) throws Exception{		Account acc = LoginUserHolder.getLoginAccount();		if (acc == null || StringUtils.isBlank(acc.getAccount())) {			return toLogin;		}//		getSession().setAttribute(FrontContainer.selectMenu,FrontContainer.not_select_menu);//		selectLeftMenu = "orders";		PagerModel pager = getMyOrders(acc.getAccount());		model.addAttribute("pager", pager);				//查询汇总		OrderSimpleReport orderSimpleReport = orderService.selectOrdersSimpleReport(acc.getAccount());		model.addAttribute("orderSimpleReport", orderSimpleReport);		logger.error("orderSimpleReport="+orderSimpleReport);		return "/account/orders";	}		/**	 * 分页查询订单集合	 * @return	 * @throws Exception	 */	private PagerModel selectMyOrders(String account) throws Exception {		int offset = 0;		if (RequestHolder.getRequest().getParameter("pager.offset") != null) {			offset = Integer					.parseInt(RequestHolder.getRequest().getParameter("pager.offset"));		}		if (offset < 0)			offset = 0;				PagerModel pager = new PagerModel();		Order order = new Order();		order.setAccount(account);		(order).setOffset(offset);		pager = orderService.selectPageList(order);		if(pager==null)pager = new PagerModel();		// 计算总页数		pager.setPagerSize((pager.getTotal() + pager.getPageSize() - 1)				/ pager.getPageSize());		//		selectListAfter();		pager.setPagerUrl("orders");		return pager;	}	/**	 * 分页获取我的订单列表，首页分页查询订单集合，然后把查询到的ID集合仍到一个多表联合的查询里面，查询出更多的信息。分页显示用户的订单只用一个SQL貌似不好搞的。想到好办法再去优化。	 * @throws Exception	 */	private PagerModel getMyOrders(String account) throws Exception {		//分页查询订单ID集合//		super.selectList();		//1、分页查询订单集合		PagerModel pager = selectMyOrders(account);		//根据上面查询出来的ID集合，多表联合查询出订单和订单明细数据		List<Order> ordersTemp = pager.getList();		List<String> ids = new LinkedList<String>();		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		for(int i=0;i<ordersTemp.size();i++){			Order orderItem = ordersTemp.get(i);			//时间转换成可以阅读的格式			orderItem.setCreatedate(DateTimeUtil.getDateTimeString(sdf.parse(orderItem.getCreatedate())));			ids.add(orderItem.getId());		}				Order order = new Order();		order.clear();		order.setAccount(account);		order.setQueryOrderIDs(ids);		//2、查询指定订单集合的所有订单项集合，然后内存中对订单项进行分组		List<Order> myOrders = orderService.selectList(order);		if(myOrders!=null && myOrders.size()>0){			for(int i=0;i<ordersTemp.size();i++){				Order orderItem = ordersTemp.get(i);				for(Iterator<Order> it = myOrders.iterator();it.hasNext();){					Order orderdetail = it.next();//					logger.error("orderdetail.getId()="+orderdetail.getId());//					logger.error("orderItem.getId()="+orderItem.getId());					if(orderdetail.getId().equals(orderItem.getId())){						orderItem.getOrders().add(orderdetail);						it.remove();					}				}			}		}		//		Map<String, Order> orderMap = new HashMap<String, Order>();		//处理成页面显示的数据格式//		if(myOrders!=null && myOrders.size()>0){//			orderMap.clear();//			for(int i=0;i<myOrders.size();i++){//				order = myOrders.get(i);//				Order entry = orderMap.get(order.getId());//				if(entry==null){//					//添加订单//					orderMap.put(order.getId(), order);//					//添加订单项//					order.getOrders().add(order);//					continue;//				}//				//				//否则添加订单到此MAP订单的orders集合中，此集合存储的是订单明细信息//				entry.getOrders().add(order);//			}//			myOrders.clear();//			myOrders.addAll(orderMap.values());//			orderMap.clear();////			//根据订单ID排序//			Collections.sort(myOrders, new Comparator<Order>() {//				@Override//				public int compare(Order o1, Order o2) {//					int id1 = Integer.valueOf(o1.getId());//					int id2 = Integer.valueOf(o2.getId());//					if (id1 > id2) {//						return 1;//					} else if (id1 < id2) {//						return 2;//					}//					return 0;//				}//			});//			getPager().setList(myOrders);//		}//		getSession().setAttribute(FrontContainer.selectMenu, "user_centers");		return pager;	}		/**	 * 转到修改密码	 * @return	 */	@RequestMapping("topwd")	public String topwd(ModelMap model, @ModelAttribute("e") Account e){		if (LoginUserHolder.getLoginAccount() == null) {			return toLoginRedirect;		}//		selectLeftMenu = "topwd";		return "/account/topwd";	}		/**	 * 修改密码	 * @return	 */	@RequestMapping("changePwd")	public String changePwd(ModelMap model, @ModelAttribute("e")Account e, RedirectAttributes flushAttrs){		Account acc = LoginUserHolder.getLoginAccount();		if (acc == null || StringUtils.isBlank(acc.getAccount())) {			return toLogin;		}		if (StringUtils.isBlank(e.getNewPassword())				|| StringUtils.isBlank(e.getNewPassword2())				|| StringUtils.isBlank(e.getPassword()) 				|| !e.getNewPassword2().equals(e.getNewPassword())) {			throw new NullPointerException();		}//		getSession().setAttribute(FrontContainer.selectMenu,FrontContainer.not_select_menu);//		selectLeftMenu = "changePwd";//		selectLeftMenu = "topwd";//		logger.error(">>e.getNewPassword() = "+e.getNewPassword());		e.setPassword(MD5.md5(e.getNewPassword()));		e.setId(acc.getId());//		logger.error(">>e.getPassword() = "+e.getPassword());		accountService.update(e);		flushAttrs.addFlashAttribute("errorMsg", "修改密码成功！");		//重新缓存密码数据		acc.setPassword(e.getPassword());				e.clear();		return "redirect:/account/changePwdSuccess";	}	@RequestMapping("changePwdSuccess")	public String changePwdSuccess(){		return "/account/changePwdSuccess";	}	//	private void setSelectMenu(String selectID){//		getSession().setAttribute(FrontContainer.selectMenu, selectID);//	}		//	/**//	 * 帮助中心//	 * @return//	 *///	public String help() throws Exception {//		logger.error("this.helpCode="+this.helpCode);//		if(StringUtils.isBlank(this.helpCode)){//			throw new NullPointerException("helpCode参数不能为空");//		}else if(this.helpCode.equals("index")){//			return "help";//		}else{//			News newsParam = new News();//			newsParam.setCode(helpCode);//			news = newsService.selectSimpleOne(newsParam);//			if(news==null){//				throw new NullPointerException("根据code查询不到文章！");//			}//			//			String url = "/jsp/helps/"+news.getId()+".jsp";//			logger.error("url = " + url);//			getRequest().setAttribute("newsInfoUrl",url);//			//			return "help";//			////			logger.error("SystemManager.newsMap="+SystemManager.newsMap);////			news = SystemManager.newsMap.get(this.helpCode);//newsService.selectById(String.valueOf(helpID));////			if(news==null){////				throw new NullPointerException("根据code查询不到文章！");////			}//		}////		return "help";//	}		/**	 * 设置选中的	 * @return	 */	@RequestMapping("setAddressDefault")	@ResponseBody	public String setAddressDefault(ModelMap model, Address e){		String id = e.getId();		if(StringUtils.isBlank(id)){			throw new NullPointerException("默认地址ID不能为空！");		}				Account account = LoginUserHolder.getLoginAccount();		if(account==null || StringUtils.isBlank(account.getAccount())){			throw new NullPointerException("账号不能为空！");		}		Address address = new Address();		address.setId(id);		address.setIsdefault("y");		address.setAccount(account.getAccount());		addressService.setAddressDefault(address);		return "{\"success\":0}";	}		/**	 * 根据省份编码获取城市列表	 * @return	 * @throws IOException 	 */	@RequestMapping("selectCitysByProvinceCode")	@ResponseBody	public String selectCitysByProvinceCode() throws IOException{		logger.error("selectCitysByProvinceCode...");		String provinceCode = RequestHolder.getRequest().getParameter("provinceCode");		logger.error("selectCitysByProvinceCode...provinceCode="+provinceCode);		if(StringUtils.isBlank(provinceCode)){			throw new NullPointerException("provinceCode is null");		}		//		Area area = new Area();//		area.setCode(provinceCode);        Map<String, Area> areaMap = SystemManager.getInstance().getAreaMap();		if(areaMap!=null && areaMap.size()>0){			Area areaInfo = areaMap.get(provinceCode);						logger.error("areaInfo = " + areaInfo);						if(areaInfo!=null && areaInfo.getChildren()!=null && areaInfo.getChildren().size()>0){				String jsonStr = JSON.toJSONString(areaInfo.getChildren());				logger.error("jsonStr=" + jsonStr);				return (jsonStr);			}		}				return ("{}");	}	/**	 * 根据城市编码获取区域列表	 * @return	 * @throws IOException 	 */	@RequestMapping("selectAreaListByCityCode")	@ResponseBody	public String selectAreaListByCityCode() throws IOException{		logger.error("selectAreaListByCityCode...");		String provinceCode = RequestHolder.getRequest().getParameter("provinceCode");		String cityCode = RequestHolder.getRequest().getParameter("cityCode");		logger.error("selectAreaListByCityCode...provinceCode="+provinceCode+",cityCode="+cityCode);		if(StringUtils.isBlank(provinceCode) || StringUtils.isBlank(cityCode)){			throw new NullPointerException("provinceCode or cityCode is null");		}        Map<String, Area> areaMap = SystemManager.getInstance().getAreaMap();		if(areaMap!=null && areaMap.size()>0){			Area city = areaMap.get(provinceCode);						logger.error("areaInfo = " + city);						if(city!=null && city.getChildren()!=null && city.getChildren().size()>0){				for(int i=0;i<city.getChildren().size();i++){					Area item = city.getChildren().get(i);					if(item.getCode().equals(cityCode)){						if(item.getChildren()!=null && item.getChildren().size()>0){							String jsonStr = JSON.toJSONString(item.getChildren());							logger.error("jsonStr=" + jsonStr);							return (jsonStr);						}					}				}			}		}				return ("{}");	}		/**	 * 转到我的积分	 * @param model	 * @param e	 * @return	 */	@RequestMapping("score")	public String score(ModelMap model, @ModelAttribute("e") Account e){		Account acc = LoginUserHolder.getLoginAccount();		if (LoginUserHolder.getLoginAccount() == null) {			return toLoginRedirect;		}		e = accountService.selectById(acc.getId());		model.addAttribute("e", e);		return "/account/score";	}	/**	 * 用户使用邮件重置密码	 * @return	 */	@RequestMapping("reset")	public String reset(ModelMap model,@ModelAttribute("e") Account account){		checkSendEmail(model, account);		return "/account/reset";	}		/**	 * 系统发出邮件后，用户访问邮件中的URL地址，此方法检查该地址的有效性和时间的有效性	 */	private Email checkSendEmail(ModelMap model, Account account){		String sign = RequestHolder.getRequest().getParameter("sign");		if(StringUtils.isBlank(sign)){			throw new NullPointerException("参数非法!");		}				//查询邮件是否是本系统所发出的		Email email = new Email();		email.setSign(sign);		email = emailService.selectOne(email);		if(email==null){			throw new NullPointerException("非法请求！");		}				if(email.getStatus().equals(email.email_status_y)){			model.addAttribute(FrontContainer.reset_password_email_timeout, "当前连接已失效！");			return null;		}		//		String email_id = email.getId();				account.setAccount(email.getAccount());		//检查此邮件是否过期		long time1 = Long.valueOf(email.getStarttime());		long time2 = new Date().getTime();		long time3 = Long.valueOf(email.getEndtime());		if (time2 > time1 && time2 < time3) {			//更新邮件状态为已失效			Email email2 = new Email();			email2.setStatus(email.email_status_y);			email2.setId(email.getId());			emailService.update(email2);						//允许修改密码			return email;		}else{			logger.error("邮件已过期！");			model.addAttribute(FrontContainer.reset_password_email_timeout, "当前连接已失效！");		}		return null;	}		/**	 * 通过邮件重置密码	 * @return	 * @throws IOException 	 */	@RequestMapping("doReset")	public String doReset(ModelMap model, Account e) throws IOException{		logger.error("doReset...");		if(StringUtils.isBlank(e.getAccount()) || StringUtils.isBlank(e.getPassword()) || StringUtils.isBlank(e.getPassword2())){			throw new NullPointerException("请求非法！");		}				if(!e.getPassword().equals(e.getPassword2())){//			getRequest().setAttribute(FrontContainer.show_user_option_error, "两次输入的密码不一致！");			throw new RuntimeException("两次输入的密码不一致！");		}		logger.error("doReset...e.getPassword() = "+e.getPassword());		Account acc = new Account();		acc.setAccount(e.getAccount());		acc.setPassword(MD5.md5(e.getPassword()));		accountService.updatePasswordByAccount(acc);		return "redirect:/account/resetSuccess";//		return "resetSuccess";	}	@RequestMapping("resetSuccess")	public String resetSuccess(){		return "/account/resetSuccess";	}		/**	 * 转到修改邮箱页面	 * @return	 */	@RequestMapping("changeEmail")	public String changeEmail(){		Account acc = LoginUserHolder.getLoginAccount();		if (acc == null || StringUtils.isBlank(acc.getAccount())) {			return toLoginRedirect;		}		return "/account/changeEmail";	}		/**	 * 修改邮箱	 * @return	 * @throws Exception 	 */	@RequestMapping("doChangeEmail")	public String doChangeEmail(Account e) throws Exception{		logger.error("e.getNewEmail() = "+e.getNewEmail());		Account acc = LoginUserHolder.getLoginAccount();		if (acc == null || StringUtils.isBlank(acc.getAccount())) {			return toLogin;		}				logger.error("doChangeEmail..");//		if(!TokenUtil.getInstance().isTokenValid(getRequest())){//			throw new Exception("表单重复提交了！");//		}		e.setId(acc.getId());		if(StringUtils.isBlank(e.getPassword()) || StringUtils.isBlank(e.getNewEmail())){			throw new NullPointerException("非法请求！");		}		//		Account acc = (Account)getSession().getAttribute(FrontContainer.USER_INFO);		if(!MD5.md5(e.getPassword()).equals(acc.getPassword())){			//前台AJAX检查密码出问题了，后台来处理前端的不足			throw new RuntimeException("出现错误，请联系系统管理员！");		}				//发送邮件到指定邮箱。		acc.setNewEmail(e.getNewEmail());		accountService.sendEmail(acc,NotifyTemplate.email_change_email);		acc.setNewEmail(null);		return "redirect:/account/changeEmailWait.html";	}	@RequestMapping("changeEmailWait")	public String changeEmailWait(){		logger.error("changeEamilWait..");		return "account/changeEmailWait";	}		/**	 * 修改邮箱--->用户登陆邮箱后点击邮件---->激活邮箱---->调用此方法	 * @return	 */	@RequestMapping("active")	public String active(ModelMap model, Account account){		logger.error("active...");//		selectLeftMenu = "user";				String sign = RequestHolder.getRequest().getParameter("sign");//		String type = getRequest().getParameter("type"); 		if(StringUtils.isBlank(sign)){			throw new NullPointerException("非法请求！");		}		Email email = checkSendEmail(model, account);		if(email!=null){			Account acc = new Account();			acc.setEmail(email.getNewEmail());			acc.setAccount(email.getAccount());			accountService.updateEmailByAccount(acc);						//修改邮箱成功后，更新session缓存中数据			acc = LoginUserHolder.getLoginAccount();			if (acc != null && StringUtils.isNotBlank(acc.getAccount())) {				acc.setEmail(email.getNewEmail());			}						email = new Email();			email.setStatus(email.email_status_n);			email.setPageMsg("恭喜：新邮箱已激活！");			model.addAttribute(FrontContainer.reset_password_email_timeout, email);		}else{			email = new Email();			email.setStatus(email.email_status_y);			email.setPageMsg("当前连接已失效！");			model.addAttribute(FrontContainer.reset_password_email_timeout, email);		}		return "/account/active";	}	/**	 * 激活账号的邮件的回调	 * @return	 */	@RequestMapping("activeAccount")	public String activeAccount(HttpServletRequest request){		logger.error("active...");		String sign = request.getParameter("sign");		if(StringUtils.isBlank(sign)){			throw new NullPointerException("非法请求！");		}				//查询邮件是否是本系统所发出的		Email email = new Email();		email.setSign(sign);		email = emailService.selectOne(email);		if(email==null){			throw new NullPointerException("非法请求！");		}				if(email.getStatus().equals(Email.email_status_y)){			request.setAttribute("LinkInvalid", "链接已失效！");			return "/account/reg_success_active_result";		}				Account acc = new Account();		acc.setAccount(email.getAccount());		acc = accountService.selectOne(acc);		if(acc==null){			throw new NullPointerException("非法请求！"); 		}				Account acc2 = new Account();		acc2.setId(acc.getId());		acc2.setEmailIsActive(Account.account_emailIsActive_y);		accountService.updateDataWhenActiveAccount(acc2,acc.getAccount());				return "/account/reg_success_active_result";	}	public static void main(String[] args) {		System.out.println(MD5.md5("123456"));	}}