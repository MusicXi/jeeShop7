package com.zlkj.ssm.shop.component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.zlkj.ssm.shop.common.basic.Base;
import com.zlkj.ssm.shop.common.basic.BaseMethodEx;
import com.zlkj.ssm.shop.common.directive.AbstractTemplateDirective;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModelException;
/**
 * FreemarkerDirectiveComponent 指令处理组件
 */
public class FreemarkerDirectiveComponent extends Base{ 
		@Autowired   
		private Configuration configuration; 
		private String directiveRemoveRegex;
		private String methodRemoveRegex;
	    private Map<String, AbstractTemplateDirective> templateDirectiveMap = new HashMap<String, AbstractTemplateDirective>();
	    private Map<String, BaseMethodEx> methodExMap = new HashMap<String, BaseMethodEx>();
	   /**
	    * 取出所有的自定义指令 实现TemplateDirectiveModel 包括 实现TemplateMethodModelEx类的自定义指令  装载到Map里面
	    * @param templateDirectiveList 装载 实现TemplateDirectiveModel类的自定义指令
	    * @param methodList 装载  实现TemplateMethodModelEx类的自定义方法指令
	    * @throws TemplateModelException 
	    */
	    @Autowired
	    private void init(List<AbstractTemplateDirective> templateDirectiveList, List<BaseMethodEx> methodList) throws TemplateModelException {
	    	for(AbstractTemplateDirective directive:templateDirectiveList){
	    		if(empty(directive.getName())){
	    			  directive.setName(uncapitalize(directive.getClass().getSimpleName().replaceAll(directiveRemoveRegex, BLANK)));
	    		}
	    		  templateDirectiveMap.put(directive.getName(), directive);
	    		  log.info(new StringBuilder().append(templateDirectiveMap.size()).append(" template directives created:")
	    	                .append(templateDirectiveMap.keySet()).toString());
	    	}
	        for (BaseMethodEx method : methodList) {
	            if (empty(method.getName())) {
	                method.setName(uncapitalize(method.getClass().getSimpleName().replaceAll(methodRemoveRegex, BLANK)));
	            }
	            methodExMap.put(method.getName(), method);
	        }
	        log.info(new StringBuilder().append(methodExMap.size()).append(" methods created:").append(methodExMap.keySet()).toString());
	       /**
	        * 注册所有的自定义指令 到Freemarker configuration对象里面 类似xml配置的 freeMarkerConfigurer.setFreemarkerVariables(Map);
	        */
	        configuration.setAllSharedVariables(new SimpleHash(templateDirectiveMap, this.configuration.getObjectWrapper()));
	        configuration.setAllSharedVariables(new SimpleHash(methodExMap, this.configuration.getObjectWrapper()));
	        log.info("【============================>>>>>【注册自定义的Freemarker 指令完成!】");
	    }
	    public String getDirectiveRemoveRegex() {
			return directiveRemoveRegex;
		}
		public void setDirectiveRemoveRegex(String directiveRemoveRegex) {
			this.directiveRemoveRegex = directiveRemoveRegex;
		}
		public String getMethodRemoveRegex() {
			return methodRemoveRegex;
		}
		public void setMethodRemoveRegex(String methodRemoveRegex) {
			this.methodRemoveRegex = methodRemoveRegex;
		}
		public Map<String, AbstractTemplateDirective> getTemplateDirectiveMap() {
			return templateDirectiveMap;
		}
		public Map<String, BaseMethodEx> getMethodExMap() {
			return methodExMap;
		}
	}
