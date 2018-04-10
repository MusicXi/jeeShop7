package com.zlkj.ssm.shop.freemarker.methodex;
import freemarker.template.TemplateModelException;
import java.util.List;
import org.springframework.stereotype.Component;
import com.zlkj.ssm.shop.common.basic.BaseMethodEx;
import com.zlkj.ssm.shop.core.KeyValueHelper;
@Component
public class KeyValueMethodEx extends BaseMethodEx {
    @Override
    public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
        return KeyValueHelper.get(arguments.get(0).toString());
    }
}
