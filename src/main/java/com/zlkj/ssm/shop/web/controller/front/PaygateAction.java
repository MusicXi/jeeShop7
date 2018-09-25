package com.zlkj.ssm.shop.web.controller.front;


import com.zlkj.ssm.shop.pay.alipay.config.AlipayConfig;
import com.zlkj.ssm.shop.pay.alipay.utils.AlipayNotify;
import com.zlkj.ssm.shop.pay.alipay.utils.AlipaySubmit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zlkj.ssm.shop.core.cache.provider.SystemManager;
import com.zlkj.ssm.shop.entity.common.Orderpay;
import com.zlkj.ssm.shop.front.entity.Order;
import com.zlkj.ssm.shop.front.entity.Ordership;
import com.zlkj.ssm.shop.front.entity.PayInfo;
import com.zlkj.ssm.shop.front.service.OrderService;
import com.zlkj.ssm.shop.front.service.OrderpayService;
import com.zlkj.ssm.shop.front.service.OrdershipService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller("frontPaygateAction")
@RequestMapping("paygate")
public class PaygateAction {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    SystemManager systemManager;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrdershipService ordershipService;
    @Autowired
    private OrderpayService orderpayService;
    @RequestMapping("pay")
    public void pay(String orderId, String orderPayId, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        Order order = orderService.selectById(orderId);

        if(order == null) {
            throw new NullPointerException("根据订单号查询不到订单信息！");
        }

        Ordership ordership = ordershipService.selectOne(new Ordership(orderId));
        if(ordership==null){
            throw new NullPointerException("根据订单号查询不到配送信息！");
        }
        Orderpay orderpay = orderpayService.selectById(orderPayId);
        if(orderpay==null){
            throw new NullPointerException("根据订单号查询不到配送信息！");
        }
        order.setOrderpayID(orderPayId);
        PayInfo payInfo = createPayInfo(order,ordership);
//        RequestHolder.getRequest().setAttribute("payInfo", payInfo);
        modelMap.addAttribute("payInfo", payInfo);

        ///使用的网关
/*        String paygateType = systemManager.getProperty("paygate.type");
        if("dummy".equalsIgnoreCase(paygateType)) {
            return "paygate/dummy/pay";
        }
        return "paygate/alipay/alipayapi";*/
        this.test(request, response, payInfo);
    }

    public void test(HttpServletRequest request, HttpServletResponse response, PayInfo payInfo) {
        Logger logger = LoggerFactory.getLogger(AlipayNotify.class);
		////////////////////////////////////请求参数//////////////////////////////////////
		//支付类型
		String payment_type = "1";
		//必填，不能修改
		//服务器异步通知页面路径
		String notify_url = SystemManager.getInstance().getSystemSetting().getWww()+"/alipayapi_notify_url.jsp";
		//需http://格式的完整路径，不能加?id=123这类自定义参数

		//页面跳转同步通知页面路径
		String return_url = SystemManager.getInstance().getSystemSetting().getWww()+"/alipayapi_return_url.jsp";
		//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

		//卖家支付宝帐户
		String seller_email = SystemManager.getInstance().getAlipayConfig();//"15000748603";//payInfo.getWIDseller_email();//new String(payInfo.getWIDseller_email().getBytes("ISO-8859-1"),"UTF-8");
		//必填

		//商户订单号
		String out_trade_no = payInfo.getWIDout_trade_no();//new String(payInfo.getWIDout_trade_no().getBytes("ISO-8859-1"),"UTF-8");
		//商户网站订单系统中唯一订单号，必填
		//payInfo.setWIDsubject("订单11");
		//订单名称
		String subject = payInfo.getWIDsubject();//new String(payInfo.getWIDsubject().getBytes("ISO-8859-1"),"UTF-8");
		//String subject = new String(payInfo.getWIDsubject().getBytes("ISO-8859-1"),"UTF-8");
		//必填

		//付款金额
		String price = String.valueOf(payInfo.getWIDprice());
		//必填

		//商品数量
		String quantity = "1";
		//必填，建议默认为1，不改变值，把一次交易看成是一次下订单而非购买一件商品
		//物流费用
		String logistics_fee = String.valueOf(payInfo.getLogistics_fee());//"5.00";
		//必填，即运费
		//物流类型
		String logistics_type = payInfo.getLogistics_type();//"EXPRESS";
		//必填，三个值可选：EXPRESS（快递）、POST（平邮）、EMS（EMS）
		//物流支付方式
		String logistics_payment = "BUYER_PAY";//"SELLER_PAY";
		//必填，两个值可选：SELLER_PAY（卖家承担运费）、BUYER_PAY（买家承担运费）
		//订单描述

		//String body = new String(payInfo.getWIDbody().getBytes("ISO-8859-1"),"UTF-8");
		String body = payInfo.getWIDsubject();//"描述";new String("".getBytes("ISO-8859-1"),"UTF-8");
		//商品展示地址
		//String show_url = new String(payInfo.getWIDshow_url().getBytes("ISO-8859-1"),"UTF-8");
		String show_url = payInfo.getShow_url();
		//需以http://开头的完整路径，如：http://www.xxx.com/myorder.html

		//收货人姓名
		String receive_name = payInfo.getWIDreceive_name();//new String(payInfo.getWIDreceive_name().getBytes("ISO-8859-1"),"UTF-8");
		//如：张三

		//收货人地址
		String receive_address = payInfo.getWIDreceive_address();//new String(payInfo.getWIDreceive_address().getBytes("ISO-8859-1"),"UTF-8");
		//如：XX省XXX市XXX区XXX路XXX小区XXX栋XXX单元XXX号

		//收货人邮编
		String receive_zip = payInfo.getWIDreceive_zip();//new String(payInfo.getWIDreceive_zip().getBytes("ISO-8859-1"),"UTF-8");
		//如：123456

		//收货人电话号码
		String receive_phone = payInfo.getWIDreceive_phone();//new String(payInfo.getWIDreceive_phone().getBytes("ISO-8859-1"),"UTF-8");
		//如：0571-88158090

		//收货人手机号码
		String receive_mobile = payInfo.getWIDreceive_mobile();//new String(payInfo.getWIDreceive_mobile().getBytes("ISO-8859-1"),"UTF-8");
		//如：13312341234


		//////////////////////////////////////////////////////////////////////////////////

		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_partner_trade_by_buyer");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("seller_email", seller_email);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("price", price);
		sParaTemp.put("quantity", quantity);
		sParaTemp.put("logistics_fee", logistics_fee);
		sParaTemp.put("logistics_type", logistics_type);
		sParaTemp.put("logistics_payment", logistics_payment);
		sParaTemp.put("body", body);
		sParaTemp.put("show_url", show_url);
		sParaTemp.put("receive_name", receive_name);
		sParaTemp.put("receive_address", receive_address);
		sParaTemp.put("receive_zip", receive_zip);
		sParaTemp.put("receive_phone", receive_phone);
		sParaTemp.put("receive_mobile", receive_mobile);

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<html>\n" +
                "\t<head>\n" +
                "\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "\t\t<title>支付宝纯担保交易接口</title>\n" +
                "\t</head>");
		//建立请求
		//out.println("请求支付宝...");
		try{
            PrintWriter out = response.getWriter();
            String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
            stringBuilder.append(sHtmlText).append("</html>");
            System.out.println(stringBuilder.toString());
            out.println(stringBuilder.toString());
            out.flush();
            out.close();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("异常！");
		}
    }

    /**
     * 创建支付宝的付款信息对象
     * @param order
     */
    private PayInfo createPayInfo(Order order,Ordership ordership) {
        if(order==null || ordership==null){
            throw new NullPointerException("参数不能为空！请求非法！");
        }

        PayInfo payInfo = new PayInfo();
        payInfo.setWIDseller_email(ordership.getPhone());
//		String debug = SystemManager.getInstance().get("system_debug");
        String www = SystemManager.getInstance().getSystemSetting().getWww();

        /**
         * 解决由于本地和线上正式环境提交相同的商户订单号导致支付宝出现TRADE_DATA_MATCH_ERROR错误的问题。
         * 本地提交的商户订单号前缀是test开头，正式环境提交的就是纯粹的支付订单号
         */
        if(www.startsWith("http://127.0.0.1") || www.startsWith("http://localhost")){
            payInfo.setWIDout_trade_no("test"+order.getOrderpayID());
        }else{
            payInfo.setWIDout_trade_no(order.getOrderpayID());
        }
        payInfo.setWIDsubject(order.getProductName());

        payInfo.setWIDprice(Double.valueOf(order.getPtotal()));
        payInfo.setWIDbody(order.getRemark());
//		payInfo.setShow_url(SystemManager.systemSetting.getWww()+"/product/"+payInfo.getWIDout_trade_no()+".html");
        payInfo.setShow_url(SystemManager.getInstance().getSystemSetting().getWww()+"/order/orderInfo.html?id="+order.getId());
        payInfo.setWIDreceive_name(ordership.getShipname());
        payInfo.setWIDreceive_address(ordership.getShipaddress());
        payInfo.setWIDreceive_zip(ordership.getZip());
        payInfo.setWIDreceive_phone(ordership.getTel());
        payInfo.setWIDreceive_mobile(ordership.getPhone());
        payInfo.setWIDsubject(order.getRemark());

        payInfo.setLogistics_fee(Double.valueOf(order.getFee()));
        payInfo.setLogistics_type(order.getExpressCode());

        logger.debug(payInfo.toString());
        return payInfo;
    }

    @RequestMapping("dummyPay")
    @ResponseBody
    public String dummyPay(String orderId){
        orderService.alipayNotify("WAIT_SELLER_SEND_GOODS",null,orderId,String.valueOf(System.nanoTime()));
        return "{\"success\":1}";
    }
}
