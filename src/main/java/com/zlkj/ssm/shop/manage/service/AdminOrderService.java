package com.zlkj.ssm.shop.manage.service;import java.util.List;import com.zlkj.ssm.shop.core.Services;import com.zlkj.ssm.shop.manage.entity.Order;import com.zlkj.ssm.shop.manage.entity.OrdersReport;import com.zlkj.ssm.shop.manage.entity.ReportInfo;public interface AdminOrderService extends Services<Order> {	/**	 * 修改订单总金额	 * @param e	 * @param account	 */	void updatePayMonery(Order e,String account);		/**	 * 查询一周内未完全支付的订单，并且是未审核的	 * @param order	 * @return	 */	List<Order> selectCancelList(Order order);	/**	 * 取消指定ID的订单	 * @param id	 */	void cancelOrderByID(String id);	/**	 * 查询指定时间范围内的订单的销量情况	 * @param order	 * @return	 */	List<ReportInfo> selectOrderSales(Order order);		/**	 * 查询指定时间范围内的产品的销量情况	 * @param order	 * @return	 */	List<ReportInfo> selectProductSales(Order order);		/**	 * 加载订单报表数据	 * @return	 */	OrdersReport loadOrdersReport();}