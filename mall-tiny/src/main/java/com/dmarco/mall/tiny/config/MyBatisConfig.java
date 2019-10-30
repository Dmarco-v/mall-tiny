package com.dmarco.mall.tiny.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * @author dmarco
 */
@Configuration
@MapperScan("com.dmarco.mall.tiny.mbg.mapper")
public class MyBatisConfig {

}
