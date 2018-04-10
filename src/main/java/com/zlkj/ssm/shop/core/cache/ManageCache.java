package com.zlkj.ssm.shop.core.cache;

import com.alibaba.fastjson.JSON;
import com.zlkj.ssm.shop.core.ManageContainer;
import com.zlkj.ssm.shop.core.TaskManager;
import com.zlkj.ssm.shop.core.cache.provider.SystemManager;
import com.zlkj.ssm.shop.manage.entity.AliyunOSS;
import com.zlkj.ssm.shop.manage.entity.OrdersReport;
import com.zlkj.ssm.shop.manage.entity.Oss;
import com.zlkj.ssm.shop.manage.entity.SystemSetting;
import com.zlkj.ssm.shop.manage.entity.Task;
import com.zlkj.ssm.shop.manage.service.AdminAreaService;
import com.zlkj.ssm.shop.manage.service.AdminCommentService;
import com.zlkj.ssm.shop.manage.service.AdminOrderService;
import com.zlkj.ssm.shop.manage.service.AdminOssService;
import com.zlkj.ssm.shop.manage.service.AdminProductService;
import com.zlkj.ssm.shop.manage.service.AdminSystemSettingService;
import com.zlkj.ssm.shop.manage.service.AdminTaskService;

import static com.zlkj.ssm.shop.common.basic.Base.empty;
import static com.zlkj.ssm.shop.common.basic.Base.notEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.LinkedList;
import java.util.List;

/**
 * 缓存管理器。 后台项目可以通过接口程序通知该类重新加载部分或全部的缓存
 * 
 * @author huangf
 * 
 */
@Component
public class ManageCache {
	private static final Logger logger = LoggerFactory.getLogger(ManageCache.class);
	
	/**
	 * manage后台
	 */
    @Resource
	private AdminOrderService adminOrderService;
    @Resource
	private AdminProductService productService;
    @Resource
	private AdminCommentService commentService;
    @Resource
	private AdminAreaService areaService;
    @Resource
	private AdminTaskService taskService;
    @Resource
	private AdminOssService ossService;
    @Autowired
    private AdminSystemSettingService systemSettingService;
    @Autowired
    private SystemManager systemManager;
	
	public void setOssService(AdminOssService ossService) {
		this.ossService = ossService;
	}

	public void setTaskService(AdminTaskService taskService) {
		this.taskService = taskService;
	}

	public void setAreaService(AdminAreaService areaService) {
		this.areaService = areaService;
	}

	public AdminOrderService getOrderService() {
		return adminOrderService;
	}

	public void setOrderService(AdminOrderService orderService) {
		this.adminOrderService = orderService;
	}

	public AdminProductService getProductService() {
		return productService;
	}

	public void setProductService(AdminProductService productService) {
		this.productService = productService;
	}

	public AdminCommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(AdminCommentService commentService) {
		this.commentService = commentService;
	}

    /**
	 * 加载订单报表
	 */
	public void loadOrdersReport(){
		OrdersReport ordersReport = adminOrderService.loadOrdersReport();
		if(ordersReport==null){
			ordersReport = new OrdersReport();
		}
		//加载缺货商品数
		ordersReport.setOutOfStockProductCount(productService.selectOutOfStockProductCount());

		//加载吐槽评论数
		ordersReport.setNotReplyCommentCount(commentService.selectNotReplyCount());
		
		logger.error("SystemManager.ordersReport = " + ordersReport.toString());
        systemManager.setOrdersReport(ordersReport);
	}
	
	/**
	 * 加载云存储配置信息
	 */
	public void loadOSS() {
		Oss oss = new Oss();
		oss.setStatus(Oss.oss_status_y);
		oss.setCode(Oss.code_aliyun);
		
		oss = ossService.selectOne(oss);
		if(oss!=null){
			if(oss.getCode().equals(Oss.code_aliyun)){
				if(empty(oss.getOssJsonInfo())){
					throw new NullPointerException("阿里云存储配置不能为空！");
				}
				AliyunOSS aliyunOSS = JSON.parseObject(oss.getOssJsonInfo(), AliyunOSS.class);
				if(aliyunOSS==null){
					throw new NullPointerException("阿里云配置不正确，请检查！");
				}
                systemManager.setAliyunOSS(aliyunOSS);
			}
		}else{
            systemManager.setAliyunOSS(null);
		}
	}
	
	/**
	 * 加载定时任务列表
	 */
	public void loadTask(){
		List<Task> list = taskService.selectList(new Task());
		if(list!=null){
			TaskManager.taskPool.clear();
			for(int i=0;i<list.size();i++){
				Task item = list.get(i);
				TaskManager.taskPool.put(item.getCode(),item);
			}
		}
	}
	
	/**
	 * 加载全部的缓存数据
	 * @throws Exception 
	 */
	public void loadAllCache() throws Exception {
		logger.error("ManageCache.loadAllCache...");
		loadOrdersReport();
//		readJsonArea();
		loadTask();
		loadOSS();
        loadSystemSetting();
		logger.error("后台缓存加载完毕!");
	}

    /**
     * 加载系统配置信息
     */
    public void loadSystemSetting() {
        SystemSetting systemSetting = systemSettingService.selectOne(new SystemSetting());
        if (systemSetting == null) {
            throw new NullPointerException("未设置本地环境变量，请管理员在后台进行设置");
        }

        //从环境变量中分解出图集来。
        if (notEmpty(systemSetting.getImages())) {
            String[] images = systemSetting.getImages().split(ManageContainer.product_images_spider);
            if (systemSetting.getImagesList() == null) {
                systemSetting.setImagesList(new LinkedList<String>());
            } else {
                systemSetting.getImagesList().clear();
            }

            for (int i = 0; i < images.length; i++) {
                systemSetting.getImagesList().add(images[i]);
            }
        }
        systemManager.setSystemSetting(systemSetting);
    }

	public static void main(String[] args) {
		String str = "10280|10281|10282";
		String[] arr = str.split("\\|");
		for(int i=0;i<arr.length;i++){
			System.out.println(arr[i]);
		}
	}
}
