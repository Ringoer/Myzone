# Myzone

为了搞点事情，练习一下vue和分布式

模拟qzone和xmu ssfw

## 6.16 更新

1. 在 user 服务中新增了对 message 服务的内部接口调用，使得用户在登录时会收到一封登录通知消息。同时为了正常使用 Feign，在 UserApplication 上添加了一个注解 @EnableFeignClients，并在 application.yaml 中新增了关于熔断的配置

2. 修改了 news 服务的对 url 处理的过程，现在可以正常访问艾尔之光论坛的链接

3. 大幅修改了 Message 服务，使得其更加符合前端的需求

## 6.15 更新

1. 修改了分页算法的实现方式，改为在 SQL 而不是 Service 中实现

2. 动态会按最新顺序显示

3. 新增了 Message 服务，准备实装消息通知功能（前端未完成）