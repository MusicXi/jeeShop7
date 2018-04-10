package com.zlkj.ssm.shop.freemarker.methodex;
import java.util.List;
import org.springframework.stereotype.Component;
import com.zlkj.ssm.shop.common.basic.BaseMethodEx;
import com.zlkj.ssm.shop.core.cache.provider.SystemManager;
import freemarker.template.TemplateModelException;
/**
 * 获取系统管理
 */
@Component
public class SystemManagerMethodEx extends BaseMethodEx {
    @Override
    public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
        return SystemManager.getInstance();
    }
}
