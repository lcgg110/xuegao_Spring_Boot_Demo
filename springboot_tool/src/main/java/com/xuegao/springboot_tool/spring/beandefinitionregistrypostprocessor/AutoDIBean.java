package com.xuegao.springboot_tool.spring.beandefinitionregistrypostprocessor;

import com.xuegao.springboot_tool.spring.beandefinitionregistrypostprocessor.bean.OriginBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by @author yihui in 16:24 18/10/13.
 */
public class AutoDIBean {

    private String name;

    @Autowired
    private OriginBean originBean;

    public AutoDIBean(String name) {
        this.name = name;
    }

    public String print() {
        return "[AutoDIBean] " + name + " originBean == null ? " + (originBean == null);
    }

}
