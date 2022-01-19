### simple-logger
一个优雅的记录日志工具

#### 背景
很多java业务处理都需要记录日志,普通样例如下,利用`LogUtil`工具类进行日志记录.
```java
public void doSomething(){
    //执行业务
   doMethod();
   String template = "用户%s修改了订单的配送地址：从“%s”修改到“%s”"
   LogUtil.log(orderNo, String.format(tempalte, "小明", "金灿灿小区", "银盏盏小区"),  "小明");
}
```

还有一些进阶的样例,利用自定义`注解`进行日志封装
```java
@LogRecord(content="修改了配送地址")
public void doSomething(){
    // 执行业务
    doMethod();
}
```

>两种方式都可以记录日志,工具类方式灵活,但代码入侵高,注解方式简洁,但不能自定义模板显示动态内容

下面介绍一种既保留注解优雅,同时也支持工具类的模板方式记录日志的实现方式


#### 使用

引入依赖`simple-logger-spring-boot-starter`即可,然后引用注解 `@LogRecord`标注在方法上即可,
注解有before,success,error三个执行周期,对应方法执行前,执行成功,执行失败进行日志输出,日志内容支持模板方式
具体包括以下方式

+ 普通属性模板

```java
    /**
     * 基础属性
     * @param orderNo
     * @return
     */
    @GetMapping("/create")
    @LogRecord(before = "开始生成订单", success = "订单号: {#orderNo} 已生成", error = "生成订单失败")
    public String create(String orderNo) {
        return orderNo;
    }
```
输出
``` 
2022-01-19 15:50:05.532  INFO 45636 --- [nio-8080-exec-5] o.g.hoorf.logger.aop.LogRecordAspect     : 开始生成订单
2022-01-19 15:50:05.533  INFO 45636 --- [nio-8080-exec-5] o.g.hoorf.logger.aop.LogRecordAspect     : 订单号: 123 已生成
```

+ 对象属性模板

```java
    /**
     * 对象属性
     * @param orderInfo
     * @return
     */
    @PostMapping("/discount")
    @LogRecord(before = "开始计算订单->{#orderInfo.orderNo}的优惠总额",success = "订单id->{#orderInfo.id},优惠总额->{#orderInfo.discount.discountAmount} 元")
    public OrderInfo discount(@RequestBody OrderInfo orderInfo) {
        return orderInfo;
    }
```
输出
```
2022-01-19 16:24:30.944  INFO 45636 --- [nio-8080-exec-7] o.g.hoorf.logger.aop.LogRecordAspect     : 开始计算订单->C1720817208的优惠总额
2022-01-19 16:24:30.948  INFO 45636 --- [nio-8080-exec-7] o.g.hoorf.logger.aop.LogRecordAspect     : 订单id->17208,优惠总额->100 元
```
+ 自定义处理函数模板

```java
    /**
     * 自定义处理函数
     * @param orderInfo
     * @return
     */
    @PostMapping("/fireSend")
    @LogRecord(success = "订单id->{#orderInfo.id},开始发货,发货信息->{searchSendInfo({#orderInfo.id})}")
    public OrderInfo fireSend(@RequestBody OrderInfo orderInfo){
        return orderInfo;
    }
```
输出
```
2022-01-19 16:25:25.497  INFO 45636 --- [nio-8080-exec-9] o.g.hoorf.logger.aop.LogRecordAspect     : 订单id->17208,开始发货,发货信息->发货人: 张三 发货时间: 20211202
```



#### 技术要点

+ ANTRL4 做模板自定义方法,参数解析
+ SPEL 做表达式输出
+ InheritableThreadLocal 做日志上下文存储
