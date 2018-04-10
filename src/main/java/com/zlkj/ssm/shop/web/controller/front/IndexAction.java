package com.zlkj.ssm.shop.web.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 前端首页
 */
@Controller
@RequestMapping("/")
public class IndexAction {
    @RequestMapping({"/","/index"})
    public String index() {
        return "index";
    }

}
