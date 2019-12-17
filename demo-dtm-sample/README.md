### ServiceComb分布式事务完整案例

参考链接：https://support.huaweicloud.com/devg-servicestage/cse_dtm_0005.html

示例Demo以旅游机票和酒店预订为例，booking是旅游预订业务的总入口，plane是机票预订服务，hotel是酒店预订服务。booking服务作为一个全局事务，包括plane跟hotel两个分支事务。



#### 全局发起者（booking）

全局发起者（booking服务）示例代码如下，@DTMTxBegin 定义了全局事务的边界，表示某个全局事务的开始。其中可设置appName(便于用户标识某一类事务)、timeout（超时参数）等。

此处通过分支执行是否抛出异常，来判断最终进行全局提交或者全局回滚。


    package com.huawei.paas.dtm.sample.booking;
    
    import com.huawei.middleware.dtm.client.annotations.DTMTxBegin;
    import com.huawei.middleware.dtm.client.context.DTMContext;
    import com.huawei.paas.dtm.sample.booking.api.HotelService;
    import com.huawei.paas.dtm.sample.booking.api.PlaneService;
    import org.apache.servicecomb.provider.pojo.RpcReference;
    import org.apache.servicecomb.provider.rest.common.RestSchema;
    import javax.ws.rs.Path;
    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.concurrent.TimeUnit;
    
    @RestSchema(schemaId = "booking")
    @Path(value = "booking")
    public class BookingConsumer {
        @RpcReference(microserviceName = "plane", schemaId = "plane")
        private PlaneService planeService;
        @RpcReference(microserviceName = "hotel", schemaId = "hotel")
        private HotelService hotelService;
    
        @DTMTxBegin(appName = "booking")
        @Path(value = "/book")
        public void book() throws InterruptedException {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())
                + "-"
                + DTMContext.getDTMContext().getGlobalTxId()
                + "-"
                + "start booking...");
            planeService.bookTicket();
            TimeUnit.SECONDS.sleep(10);
            hotelService.bookRoom();
            System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())
                + "-"
                + DTMContext.getDTMContext().getGlobalTxId()
                + "-"
                + "booking succeeded!");
        }
    }



#### 分支参与者（plane）

分支参与者（plane服务）示例代码如下，@DTMTccBranch 定义了分支事务的边界，其中需要配置用于分支标识的identifier及二阶段推进所要执行的confirm和cancel方法。

每个分支都有自己唯一的分支id及全局id（同个全局事务的每个分支的全局id相同），可通过调用DTMContext的接口进行获取。

    package com.huawei.paas.dtm.sample.plane;
    
    import com.huawei.middleware.dtm.client.context.DTMContext;
    import com.huawei.middleware.dtm.client.tcc.annotations.DTMTccBranch;
    import org.apache.servicecomb.provider.rest.common.RestSchema;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestMethod;
    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.concurrent.TimeUnit;
    
    @RestSchema(schemaId = "plane")
    @RequestMapping(value = "plane")
    public class PlaneService {
        @RequestMapping(value = "/bookTicket", method = RequestMethod.GET)
        @DTMTccBranch(identifier = "plane", confirmMethod = "confirm", cancelMethod = "cancel")
        public void bookTicket() throws InterruptedException {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("book plane ticket");
        }
    }

##### 二阶段推进（plane提交）示例代码

```
public void confirm() {
    System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())
            + "-"
            + DTMContext.getDTMContext().getGlobalTxId()
            + "-"
            + DTMContext.getDTMContext().getBranchTxId()
            + "confirm hotel");
}
```

##### 二阶段推进（plane回滚）示例代码

```
public void cancel() {
    System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())
            + "-"
            + DTMContext.getDTMContext().getGlobalTxId()
            + "-"
            + DTMContext.getDTMContext().getBranchTxId()
            + "cancel hotel");
}
```



#### 分支参与者（hotel）

分支参与者（hotel服务）示例代码如下，@DTMTccBranch 定义了分支事务的边界，其中需要配置用于分支标识的identifier及二阶段推进所要执行的confirm和cancel方法。

每个分支都有自己唯一的分支id及全局id（同个全局事务的每个分支的全局id相同），可通过调用DTMContext的接口进行获取。

    package com.huawei.paas.dtm.sample.hotel;
    
    import com.huawei.middleware.dtm.client.context.DTMContext;
    import com.huawei.middleware.dtm.client.tcc.annotations.DTMTccBranch;
    import org.apache.servicecomb.provider.rest.common.RestSchema;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestMethod;
    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.concurrent.TimeUnit;
    
    @RestSchema(schemaId = "hotel")
    @RequestMapping(value = "/hotel")
    public class HotelService {
        @RequestMapping(value = "/bookRoom", method = RequestMethod.GET)
        @DTMTccBranch(identifier = "hotel", confirmMethod = "confirm", cancelMethod = "cancel")
        public void bookRoom() throws InterruptedException {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("book hotel room");
        }
    }

##### 二阶段推进（hotel提交）示例代码

```
public void confirm() {
    System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())
            + "-"
            + DTMContext.getDTMContext().getGlobalTxId()
            + "-"
            + DTMContext.getDTMContext().getBranchTxId()
            + "confirm hotel");
}
```

##### 二阶段推进（hotel回滚）示例代码

```
public void cancel() {
    System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())
            + "-"
            + DTMContext.getDTMContext().getGlobalTxId()
            + "-"
            + DTMContext.getDTMContext().getBranchTxId()
            + "cancel hotel");
}
```

