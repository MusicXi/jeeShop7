package com.zlkj.ssm.shop.common.basic;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import static org.apache.commons.logging.LogFactory.getLog;
public abstract class Base extends org.apache.commons.lang3.StringUtils{
	 protected final Log log = getLog(getClass());
	 public static final String COMMA_DELIMITED = ",";
	 public static final String DEFAULT_CHARSET_NAME = "UTF-8";
	    /**
	     * 空白字符串
	     */
	public static final String BLANK = "";
	
	public static Date getDate() {
		return new Date();
	}

	public static boolean notEmpty(String var) {
		return isNotBlank(var);
	}

	public static boolean empty(String var) {
		return isBlank(var);
	}

	public static boolean notEmpty(Object var) {
		return null != var;
	}

	public static boolean notEmpty(List<?> var) {
		return null != var && !var.isEmpty();
	}

	public static boolean notEmpty(Map<?, ?> var) {
		return null != var && !var.isEmpty();
	}

	public static boolean empty(List<?> var) {
		return null == var || var.isEmpty();
	}

	public static boolean empty(Map<?, ?> var) {
		return null == var || var.isEmpty();
	}

	public static boolean empty(Object var) {
		return null == var;
	}

	public static boolean empty(File file) {
		return null == file || !file.exists();
	}

	public static boolean notEmpty(File file) {
		return null != file && file.exists();
	}

	public static boolean empty(Object[] var) {
		return null == var || 0 == var.length;
	}

	public static boolean notEmpty(Object[] var) {
		return null != var && 0 < var.length;
	}
}
