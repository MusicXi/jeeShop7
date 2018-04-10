package com.zlkj.ssm.shop.web.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dylan on 15-3-20.
 */
@Controller("frontCatalogAction")
@RequestMapping("/catalog")
@Deprecated
public class CatalogAction {
    @RequestMapping("{catalogCode}")
    public String catalog(@PathVariable("catalogCode")String catalogCode){
        return "forward:/product/productList?catalogCode="+catalogCode;
    }

    @RequestMapping("attr/{attrID}")
    public String attr(@PathVariable("attrID")String attrID){
        return "forward:/product/productList?attrID="+attrID;
    }
}
