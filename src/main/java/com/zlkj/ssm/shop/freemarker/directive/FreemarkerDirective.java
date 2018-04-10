package com.zlkj.ssm.shop.freemarker.directive;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.zlkj.ssm.shop.common.directive.AbstractTemplateDirective;
import com.zlkj.ssm.shop.common.handler.RenderHandler;
@Component
public class FreemarkerDirective  extends AbstractTemplateDirective{
	@Override
	public void execute(RenderHandler handler) throws IOException, Exception {
		handler.put("userName", "程序媛").render();
	}
}
