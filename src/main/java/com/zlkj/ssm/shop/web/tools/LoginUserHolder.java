package com.zlkj.ssm.shop.web.tools;


import javax.servlet.http.HttpSession;

import com.zlkj.ssm.shop.core.FrontContainer;
import com.zlkj.ssm.shop.core.ManageContainer;
import com.zlkj.ssm.shop.core.entity.User;
import com.zlkj.ssm.shop.front.entity.Account;

/**
 * Created by dylan on 15-2-11.
 */
public class LoginUserHolder {
    public static User getLoginUser(){
        HttpSession session = RequestHolder.getSession();
        return session == null ? null : (User)session.getAttribute(ManageContainer.manage_session_user_info);
    }
    public static Account getLoginAccount(){
        HttpSession session = RequestHolder.getSession();
        return session == null ? null : (Account)session.getAttribute(FrontContainer.USER_INFO);
    }
}
