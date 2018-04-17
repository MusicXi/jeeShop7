# jeeShop7 项目说明
> https://gitee.com/dinguangx/jshop/tree/jshop
  https://gitee.com/hyhu/zlkjeeShop_v4
  https://gitee.com/dinguangx/jshop/tree/dev-jshop-tiny
  分支项目
  
### 快速启动
1. 数据库初始化:执行sql/jeeshop.sql文件
2. 更改数据库连接配置 application.yml
3. 启动项目：com.zlkj.ssm.shop.core.config.ApplicationConfiguration run

### 项目访问
- 主页面:http://localhost/
- 后台管理:http://localhost/manage/user/login (默认用户名密码:admin/123456)

### 后续改版思路
1. 电商项目业务场景，挺适合ddd驱动设计重构。
2. swagger2 动态生成项目接口文档
3. 后端使用vue(elementUI)替代freemarker,前后端分离。(前台页面是否试用待定) 参考项目:https://github.com/PanJiaChen/vue-element-admin

### 项目扩展
1. 增加小程序端支持电商页面 参考项目:https://gitee.com/sansanC/wechatApp

### 参考项目
