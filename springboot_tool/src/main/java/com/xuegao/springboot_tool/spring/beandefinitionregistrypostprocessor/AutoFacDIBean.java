package com.xuegao.springboot_tool.spring.beandefinitionregistrypostprocessor;

import com.xuegao.springboot_tool.spring.beandefinitionregistrypostprocessor.bean.OriginBean;
import lombok.Setter;

/**
 * Created by @author yihui in 16:54 18/10/13.
 */
public class AutoFacDIBean {
    private String name;

    @Setter
    private OriginBean originBean;

    @Setter
    private AutoBean autoBean;

    public AutoFacDIBean(String name) {
        this.name = name;
    }

    public String print() {
        return "[AutoDIBean] " + name + " originBean == null ? " + (originBean == null) + " | autoBean==null ? " +
                (autoBean == null);
    }

}
