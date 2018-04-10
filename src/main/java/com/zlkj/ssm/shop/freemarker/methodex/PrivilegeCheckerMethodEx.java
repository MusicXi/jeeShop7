package com.zlkj.ssm.shop.freemarker.methodex;
import freemarker.template.TemplateModelException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zlkj.ssm.shop.common.basic.BaseMethodEx;
import com.zlkj.ssm.shop.core.PrivilegeUtil;
import com.zlkj.ssm.shop.web.tools.RequestHolder;

import javax.servlet.http.HttpSession;

import java.util.List;

@Component
public class PrivilegeCheckerMethodEx extends BaseMethodEx {
    private static Logger logger = LoggerFactory.getLogger(PrivilegeCheckerMethodEx.class);
    @Override
    public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
        if(arguments == null || arguments.size() == 0){
            return true;
        }
        if(!(arguments.get(0) instanceof String)){
            return true;
        }
        String res = (String)arguments.get(0);
        if(StringUtils.isBlank(res)){
            return true;
        }
        HttpSession session = RequestHolder.getSession();
        logger.info("check privilege ,res : {}, session id :{}", res, session == null ? null : session.getId());
        return PrivilegeUtil.check(session, res);
    }
}
