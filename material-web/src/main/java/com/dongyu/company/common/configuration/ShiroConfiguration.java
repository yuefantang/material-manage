package com.dongyu.company.common.configuration;

import com.dongyu.company.common.handler.ShiroRelam;
import com.dongyu.company.common.utils.CredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro认证登陆配置
 *
 * @author TYF
 * @date 2018/11/8
 * @since 1.0.0
 */
@Configuration
public class ShiroConfiguration {

    /**
     * 2   * cookie对象;
     * 3   * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
     * 4   * @return
     * 5
     */
//    @Bean
//    public SimpleCookie rememberMeCookie() {
//        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
//        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
//        //<!-- 记住我cookie生效时间半小时 ,单位秒;-->
//        simpleCookie.setMaxAge(1800);
//        return simpleCookie;
//    }

    /**
     * 17   * cookie管理对象;
     * 18   * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     * 19   * @return
     * 20
     */
//    @Bean
//    public CookieRememberMeManager rememberMeManager() {
//        //System.out.println("ShiroConfiguration.rememberMeManager()");
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        cookieRememberMeManager.setCookie(rememberMeCookie());
//        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
//        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
//        return cookieRememberMeManager;
//    }

    //配置自定义的权限登录器
    @Bean(name = "authRealm")
    public ShiroRelam authRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
        ShiroRelam shiroRelam = new ShiroRelam();
        shiroRelam.setCredentialsMatcher(matcher);
        return shiroRelam;
    }

    //配置自定义的密码比较器
    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(manager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/web/user/login", "anon");
        filterChainDefinitionMap.put("/web/dict", "anon");
        filterChainDefinitionMap.put("/web/**", "authc");
        //账号管理
        filterChainDefinitionMap.put("/web/user/**", "authc,roles[admin]");
        //财务相关管理
        filterChainDefinitionMap.put("/web/finance/**", "authc,roles[admin,finance]");
        //工程相关管理
        filterChainDefinitionMap.put("/web/engineering/**", "authc,roles[admin,engineering]");
       //仓库管理
        filterChainDefinitionMap.put("/web/warehouse/**", "authc,roles[admin,warehouse]");
        //品质管理
        filterChainDefinitionMap.put("/web/quality/**", "authc,roles[admin,quality]");

        filterChainDefinitionMap.put("/**", "anon");
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，重定向到自定义请求接口，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/web/user/unauth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        return shiroFilterFactoryBean;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("authRealm") ShiroRelam shiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);
        securityManager.setRememberMeManager(null);
        //注入记住我管理器
        //securityManager.setRememberMeManager(rememberMeManager());
        // <!-- 用户授权/认证信息Cache, 采用EhCache 缓存 -->
        // dwsm.setCacheManager(getEhCacheManager());
        return securityManager;
    }

    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    //自动创建代理，没有这个鉴权可能会出错,使注解生效
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * 开启shiro 注解支持.controller中的方法前加的注解 @RequiresRoles
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param manager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }
}
