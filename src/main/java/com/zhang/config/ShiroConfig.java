package com.zhang.config;

import com.zhang.realm.AuthRealm;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置文件
 */
@Configuration
public class ShiroConfig {

    // 从配置文件里面读取是否需要启动登录认证的开关，默认true
    @Value("${jwt.auth}")
    private boolean auth;

    // SecurityManager 安全管理器；Shiro的核心
    @Bean
    public DefaultWebSecurityManager getSecurityManager(Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }

    // 配置拦截器
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //启用认证
        String openAuth = auth ? "auth" : "anon";
        //自定义过滤器链
        Map<String, Filter> filters = new HashMap<>();
        //指定拦截器处理
        filters.put("auth", new AuthFilter());
        shiroFilterFactoryBean.setFilters(filters);
        Map<String, String> filterMap = new LinkedHashMap<>();
        //登录请求不拦截
        filterMap.put("/login", "anon");
        filterMap.put("/register", "anon");
        //拦截所有接口请求，做权限判断
        filterMap.put("/**", openAuth);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        System.out.println("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }

    // 自定义身份认证realm
    @Bean
    public AuthRealm userRealm() {
        return new AuthRealm();
    }


    @Bean("lifecycleBeanPostProcessor")
    // 管理shiro生命周期
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    // Shiro注解支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
