// package com.xuegao.springboot_tool.spring.DependsOn;
//
// /**
//  * <br/> @PackageName：com.xuegao.springboot_tool.spring.DependsOn
//  * <br/> @ClassName：DependsOnTest
//  * <br/> @Description：
//  * <br/> @author：xuegao
//  * <br/> @date：2020/9/14 17:16
//  */
//
// import com.xuegao.springboot_tool.spring.beandefinitionregistrypostprocessor.manual.ManualBean;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.DependsOn;
// import org.springframework.stereotype.Component;
//
// /**
//  * 用于测试bean加载顺序导致手动注册的bean无法找到的问题
//  * Created by @author yihui in 19:06 19/12/2.
//  */
// @DependsOn("beanRegisterAutoConf")
// @Component
// public class AnoOrderDIBean {
//     @Autowired
//     private ManualBean manualBean;
//
//     public AnoOrderDIBean() {
//         System.out.println("AnoOrderDIBean init: " + System.currentTimeMillis());
//     }
//
//     public String print() {
//         return "[AnoOrderDIBean] print！！！ manualBean == null ? " + (manualBean == null);
//     }
// }