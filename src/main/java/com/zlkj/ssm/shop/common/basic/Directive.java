package com.zlkj.ssm.shop.common.basic;

import java.io.IOException;

import com.zlkj.ssm.shop.common.handler.RenderHandler;


/**
 * 
 * BaseDirective 指令接口
 *
 */
public interface Directive {
    /**
     * @return
     */
    public String getName();

    /**
     * @param handler
     * @throws IOException
     * @throws Exception
     */
    public void execute(RenderHandler handler) throws IOException, Exception;
}
