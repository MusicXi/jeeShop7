package com.zlkj.ssm.shop.core.config;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

import static com.zlkj.ssm.shop.common.basic.Base.DEFAULT_CHARSET_NAME;

import com.zlkj.ssm.shop.component.FreemarkerDirectiveComponent;
import com.zlkj.ssm.shop.web.listener.SystemListener;
import com.zlkj.ssm.shop.web.servlet.UploadifyServlet;
import com.zlkj.ssm.shop.web.servlet.ValidateImage;
/**
 * SpringBootConfiguration、@EnableAutoConfiguration以及@ComponentScan，
 * 我们在开发的过程中如果不使用@SpringBootApplication，则可以组合使用这三个注解。这三个注解中，
 * @SpringBootConfiguration 实际上就是 @Configuration注解，
 * 表明这个类是一个配置类，@EnableAutoConfiguration则表示让Spring Boot根据类路径中的jar包依赖为当前项目进行自动配置，
 * 最后一个@ComponentScan的作用我也不赘述了，
 * 唯一要注意的是如果我们使用了@SpringBootApplication注解的话，系统会去入口类的同级包以及下级包中去扫描实体类，
 * 因此我们建议入口类的位置在groupId+arctifactID组合的包名下 
 * 如果我们只想要@SpringBootApplication去扫描特定的类而不是全部类，那么就可以关闭自动配置
 *  @SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
 * 
 * @EnableTransactionManagement 
 *启注解事务管理，等同于xml配置方式的 <tx:annotation-driven /> 设置proxy-target-class为true即使用cglib的方式代理对象。
 *spring boot 默认使用 jdk的动态代理只能对接口的类生成代理，cglib是针对类实现代理，主要是对指定的类实现一个子类，
 * 
 * @PropertySource 注解当前类，参数为对应的配置文件路径，这种方式加载配置文件，可不用在xml中配置PropertiesFactoryBean
 *				     引入jdbc.properties，使用时方便得多，DataSourceConfiguration不再需要成员变量，
 *				     取而代之的是需要注入一个Environment环境配置，使用env.getProperty(key)获取数据：
 *
 * #或者application.properties配置
 *		 spring.aop.auto=true # Add @EnableAspectJAutoProxy.
 *       spring.aop.proxy-target-class=false # Whether subclass-based (CGLIB) proxies are to be created (true) as opposed to standard Java interface-based proxies (false).
 * 
 * @author 程序媛
 * @2017年8月24日
 * @Description:
 */
@Configuration
@EnableAutoConfiguration
@MapperScan(value="com.zlkj.ssm.shop.*.dao")
@EnableTransactionManagement(proxyTargetClass=true)
@ComponentScan(basePackages = "com.zlkj.ssm.shop", excludeFilters = { @ComponentScan.Filter(value = { Controller.class }) })
@ServletComponentScan(basePackageClasses={SystemListener.class,UploadifyServlet.class,ValidateImage.class})
@PropertySource(value={ "classpath:config/config.properties" })
public class ApplicationConfiguration extends SpringBootServletInitializer{
	@Autowired
	private Environment env;
	@Autowired
	private DataSource druidDataSource;
	
	/**
	 * 外部tomcat 初始化配置 入口
	 * spring-boot提供的解决方案：生成tomcat服务器能管理的war包，而非内嵌的tomcat直接生成的jar包
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ApplicationConfiguration.class);
	}
	/** 
	 * 修改DispatcherServlet默认配置 默认是url-patter 为： / 改为 /*
	 */
	@Bean
	public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
		registration.getUrlMappings().clear();
		registration.addUrlMappings("/*");
		return registration;
	}
	/**
	 * 注册 urlrewrite 过滤器
	 * @return
	 */
	@Bean
    public FilterRegistrationBean urlRewriteFilterRegistrationBean() {
	        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
	        filterRegistrationBean.setFilter(new UrlRewriteFilter());
	        Map<String, String> initParameters = new HashMap<String, String>();
	        initParameters.put("logLevel", "INFO");
	        filterRegistrationBean.addUrlPatterns("/*");
	        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
	@Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(druidDataSource);
    }
	/**
	 *  注册Freemarker 指令组件
	 */
    @Bean
    public FreemarkerDirectiveComponent freemarkerDirectiveComponent() {
    	FreemarkerDirectiveComponent bean = new FreemarkerDirectiveComponent();
        bean.setDirectiveRemoveRegex(env.getProperty("freemarker.tag.directiveRemoveRegex"));
        bean.setMethodRemoveRegex(env.getProperty("freemarker.tag.methodRemoveRegex"));
        return bean;
    }

    /**
     * 配置文件上传CommonsMultipartResolver
     * @return
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver bean = new CommonsMultipartResolver();
        bean.setDefaultEncoding(DEFAULT_CHARSET_NAME);
        bean.setMaxUploadSize(Long.parseLong(env.getProperty("multipart.maxUploadSize")));
        return bean;
    }
    
    
	public static void main(String[] args) {
	 new SpringApplication(ApplicationConfiguration.class).run(args); 
	}
}
