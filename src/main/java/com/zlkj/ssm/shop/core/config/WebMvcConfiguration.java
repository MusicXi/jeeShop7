package com.zlkj.ssm.shop.core.config;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 扫描Controller  组件
 * @ComponentScan是告诉Spring 哪个packages 的用注解标识的类 会被spring自动扫描并且装入bean容器。
 *	基本的basePackages参数是用于扫描带注释组件的基本包。。那么excludeFilters呢？其他参数呢？反正百度前三页列表没有答案（没有黑百度的意思）。然后找文档，百度翻译了一下（百度翻译还挺好用的。）
 *	basePackageClasses：对basepackages()指定扫描注释组件包类型安全的替代。
 *	excludeFilters：指定不适合组件扫描的类型。
 *	includeFilters：指定哪些类型有资格用于组件扫描。
 *	lazyInit：指定是否应注册扫描的beans为lazy初始化。
 *	nameGenerator：用于在Spring容器中的检测到的组件命名。
 *	resourcePattern：控制可用于组件检测的类文件。
 *	scopedProxy：指出代理是否应该对检测元件产生，在使用过程中会在代理风格时尚的范围是必要的。
 *	scopeResolver：用于解决检测到的组件的范围。
 *	useDefaultFilters：指示是否自动检测类的注释 @Component@Repository, @Service, or @Controller 应启用。
 * @author 程序媛
 * @2017年6月27日
 * @Description:

 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.zlkj.ssm.shop.web.controller", useDefaultFilters = false,includeFilters = {@ComponentScan.Filter(value = { Controller.class }) })
public class WebMvcConfiguration  extends WebMvcConfigurerAdapter{
		/**
		 * 释放静态资源
		 */
		@Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		  registry.addResourceHandler("/**").addResourceLocations("/");//代表映射 webapp目录下的所有资源 进行静态资源释放
		  /*
			 registry.addResourceHandler("/resource/**").addResourceLocations("/resource/");
			 registry.addResourceHandler("/attached/**").addResourceLocations("/attached/");
		     registry.addResourceHandler("/images/**").addResourceLocations("/images/");
		     registry.addResourceHandler("/upload/**").addResourceLocations("/upload/");
	     */
	    }
		
		  /**
	     * 国际化处理
	     * @return
	     */
	    @Bean
	    public MessageSource messageSource() {
	        ResourceBundleMessageSource bean = new ResourceBundleMessageSource();
	        bean.setBasenames(new String[] {"i18n.jeeshop"});
	        bean.setCacheSeconds(300);
	        bean.setUseCodeAsDefaultMessage(true);
	        return bean;
	    }


	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		return converter;
	}
	@Bean
	public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter(){
	return 	new MappingJackson2HttpMessageConverter();
	}


	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(responseBodyConverter());
		converters.add(mappingJacksonHttpMessageConverter());
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false);
	}
}
