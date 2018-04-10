package com.zlkj.ssm.shop.freemarker.methodex;
import java.util.List;
import org.springframework.stereotype.Component;
import com.zlkj.ssm.shop.common.basic.BaseMethodEx;
import com.zlkj.ssm.shop.core.i18n.MessageLoader;
import freemarker.template.TemplateModelException;
/**
 * 国际化配置
 */
@Component
public class I18NMethodEx extends BaseMethodEx {
    @Override
    public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
        return MessageLoader.instance().getMessage(arguments.get(0).toString());
    }

}
