package com.xuegao.springboot_tool.controller;

import com.xuegao.springboot_tool.constant.aop.annotation.MyTest;
import com.xuegao.springboot_tool.constant.aop.annotation.PrintlnLog;
import com.xuegao.springboot_tool.model.po.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <br/> @PackageName：com.xuegao.springboot_tool.controller
 * <br/> @ClassName：UserInfoController
 * <br/> @Description：
 * <br/> @author：xuegao
 * <br/> @date：2020/6/28 10:21
 */
@RestController
public class UserInfoController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @PrintlnLog(description = "主页详情-indexController")
    @RequestMapping(path = {"/", "/index.html", "/index"}, method = RequestMethod.GET)
    public String indexController() {
        // log.info("indexController = " + "hello");

        // 代码里面有异常，会进入AfterThrowing
        // int a = 2 / 0;

        System.out.println("indexController = " + "hello");
        return "hello";
    }

    // @PrintlnLog(description = "主页详情-test1Controller")
    @MyTest
    @RequestMapping(path = "/test1", method = RequestMethod.GET)
    public String test1Controller() {
        // log.info("indexController = " + "hello");

        // 代码里面有异常，会进入AfterThrowing
        // int a = 2 / 0;

        System.out.println("test1Controller = " + "test1");
        return "test1";
    }

    @RequestMapping(path = "/test1", method = RequestMethod.POST)
    public String test3Controller(@RequestBody @Valid UserInfo userInfo) {
        // log.info("indexController = " + "hello");

        // 代码里面有异常，会进入AfterThrowing
        // int a = 2 / 0;

        System.out.println("test1Controller = " + "test1");
        return "test1";
    }
}