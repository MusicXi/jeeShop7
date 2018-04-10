package com.zlkj.ssm.shop.web.servlet;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.DispatcherServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by dylan on 15/7/12.
 */
public class MultipartDispatcherServlet extends DispatcherServlet {
	private static final long serialVersionUID = 1L;
	@Override
    protected HttpServletRequest checkMultipart(HttpServletRequest request) throws MultipartException {
        HttpServletRequest servletRequest = super.checkMultipart(request);
        if(servletRequest instanceof  MultipartHttpServletRequest
                && !(RequestContextHolder.currentRequestAttributes() instanceof MultipartServletRequestAttributes)) {
            RequestContextHolder.setRequestAttributes(new MultipartServletRequestAttributes(servletRequest));
        }
        return servletRequest;
    }
	public MultipartDispatcherServlet(WebApplicationContext webApplicationContext) {
		super(webApplicationContext);
	}

    public static class MultipartServletRequestAttributes extends ServletRequestAttributes {
        /**
         * Create a new ServletRequestAttributes instance for the given request.
         * @param request current HTTP request
         */
        public MultipartServletRequestAttributes(HttpServletRequest request) {
            super(request);
        }
    }
}
