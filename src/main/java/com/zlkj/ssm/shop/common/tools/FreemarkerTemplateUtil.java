package com.zlkj.ssm.shop.common.tools;
import java.io.StringWriter;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerTemplateUtil {

	public static String freemarkerProcess(@SuppressWarnings("rawtypes") Map input, String templateStr) {  
	    try {
	    	StringTemplateLoader stringLoader = new StringTemplateLoader();  
		    String template = "content";  
		    stringLoader.putTemplate(template, templateStr);  
		    Configuration cfg = new Configuration(Configuration.VERSION_2_3_26);  
		    cfg.setTemplateLoader(stringLoader);  

		    Template templateCon = cfg.getTemplate(template);  
		    StringWriter writer = new StringWriter();  
		    templateCon.process(input, writer);  
		    return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return null;
	}  
}
