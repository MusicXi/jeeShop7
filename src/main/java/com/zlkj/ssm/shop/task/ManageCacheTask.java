package com.zlkj.ssm.shop.task;

import java.util.concurrent.TimeUnit;







import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zlkj.ssm.shop.core.cache.ManageCache;
import com.zlkj.ssm.shop.core.cache.provider.SystemManager;

/**
 * 后台缓存定时更新
 * 
 * @author huangf
 * 
 */
@Component
public class ManageCacheTask implements Runnable {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ManageCacheTask.class);
//	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private ManageCache manageCache;

	public void setManageCache(ManageCache manageCache) {
		this.manageCache = manageCache;
	}

	@Override
	public void run() {
		while (true) {
			
			try {
//				TimeUnit.MINUTES.sleep(15);//单位：分钟
				TimeUnit.SECONDS.sleep(Long.valueOf(SystemManager.getInstance().getProperty("task_SystemAutoNotifyTask_time")));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			logger.error("OrderCancelTask.run...");
			try {
				manageCache.loadAllCache();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
