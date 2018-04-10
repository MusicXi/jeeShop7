package com.zlkj.ssm.shop.freemarker.methodex;
import freemarker.template.TemplateModelException;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zlkj.ssm.shop.common.basic.BaseMethodEx;
import com.zlkj.ssm.shop.core.FrontContainer;
import com.zlkj.ssm.shop.web.tools.RequestHolder;
/**
 * 获取购物车
 */
@Component
public class ShoppingCartMethodEx extends BaseMethodEx  {
    @Override
    public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
        return RequestHolder.getSession().getAttribute(FrontContainer.myCart);
    }
}
