package com.xuegao.springboot_tool.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 示例配置文件
 *
 * @author lengleng
 * @date 2020/6/10
 */
@Component
@ConfigurationProperties("demo")
public class DemoConfig {

    private String username;

    private String password;
}