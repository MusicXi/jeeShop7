package com.zlkj.ssm.shop.common.directive;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
/**
 *AbstractTemplateDirective 自定义模板指令基类
 */
public abstract class AbstractTemplateDirective extends BaseTemplateDirective {
    @Override
    public void execute(HttpMessageConverter<Object> httpMessageConverter, MediaType mediaType, HttpServletRequest request,
            String callback, HttpServletResponse response) throws IOException, Exception {
    }
}