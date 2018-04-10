package com.zlkj.ssm.shop.freemarker.methodex;
import java.util.List;
import org.springframework.stereotype.Component;
import com.zlkj.ssm.shop.common.basic.BaseMethodEx;
import com.zlkj.ssm.shop.core.cache.provider.SystemManager;
import freemarker.template.TemplateModelException;
/**
 * 获取系统参数的配置
 */
@Component
public class SystemSettingMethodEx extends BaseMethodEx  {
    @Override
    public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
        System.out.println( SystemManager.getInstance().getSystemSetting());
        return SystemManager.getInstance().getSystemSetting();
    }
}
