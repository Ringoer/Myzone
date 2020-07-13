# Myzone

> 为了搞点事情，练习一下vue和分布式
>
> 模拟qzone和xmu ssfw

## 性能分析

| 模块名   | CPU  | MEM  | 带宽 | 理由                                                         |
| -------- | ---- | ---- | ---- | ------------------------------------------------------------ |
| eureka   | -    | -    | +    | 经常被zuul访问，以保持zuul的实例列表时效性                   |
| zuul     | +++  | +++  | +++  | 作为整个应用的入口，不仅要识别请求，还要转发请求，是性能瓶颈之所在 |
| user     | +    | +    | +    | 用户登录是最常访问的功能，需要快速响应，但其他功能并不常用   |
| course   | -    | -    | -    | 实现对用户自己的课程的增删改查，是一般功能                   |
| dynamics | ++   | ++   | ++   | 实现对用户自己动态的增删改查以及对好友的查，是较常用功能     |
| message  | +++  | +++  | +++  | 站内信是非常常用的功能，必须保证性能                         |
| news     | -    | -    | -    | 目前只能获取艾尔之光新闻，并不常访问                         |

结论：

zuul必须保证性能，使用主机1运行，同时绑定关联密切的eureka

其余均在主机2上运行

## 部署计划

| 模块名   | docker镜像名    | docker实例名    | 主机编号 |
| -------- | --------------- | --------------- | -------- |
| eureka   | myzone-eureka   | myzone-eureka   | 1        |
| zuul     | myzone-zuul     | myzone-zuul     | 1        |
| user     | myzone-user     | myzone-user     | 2        |
| course   | myzone-course   | myzone-course   | 2        |
| dynamics | myzone-dynamics | myzone-dynamics | 2        |
| message  | myzone-message  | myzone-message  | 2        |
| news     | myzone-news     | myzone-news     | 2        |

## 7.13 更新

完善了用户注册部分逻辑，增加了邮件验证码判定

## 6.17 更新

1. 新增了按类型、发件人、已读/未读查询消息的功能
3. 修改了判断是否是系统消息的逻辑，将其从 message 服务转移到 user 服务中执行
5. 修改了 user 服务中的注册逻辑

## 6.16 更新 2

1. 发送消息时，要反过来查询 user 服务，以验证发件人和收件人的合法性
2. 修改了 message 表结构

## 6.16 更新

1. 在 user 服务中新增了对 message 服务的内部接口调用，使得用户在登录时会收到一封登录通知消息。同时为了正常使用 Feign，在 UserApplication 上添加了一个注解 @EnableFeignClients，并在 application.yaml 中新增了关于熔断的配置

2. 修改了 news 服务的对 url 处理的过程，现在可以正常访问艾尔之光论坛的链接

3. 大幅修改了 Message 服务，使得其更加符合前端的需求

## 6.15 更新

1. 修改了分页算法的实现方式，改为在 SQL 而不是 Service 中实现

2. 动态会按最新顺序显示

3. 新增了 Message 服务，准备实装消息通知功能（前端未完成）