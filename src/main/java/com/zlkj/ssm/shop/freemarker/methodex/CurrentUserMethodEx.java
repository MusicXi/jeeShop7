package com.zlkj.ssm.shop.freemarker.methodex;
import freemarker.template.TemplateModelException;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zlkj.ssm.shop.common.basic.BaseMethodEx;
import com.zlkj.ssm.shop.web.tools.LoginUserHolder;
/**
 * 获取当前登录的用户
 * Created by dylan on 15-1-15.
 */
@Component
public class CurrentUserMethodEx extends BaseMethodEx {
    @Override
    public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
        return LoginUserHolder.getLoginUser();
    }
}
