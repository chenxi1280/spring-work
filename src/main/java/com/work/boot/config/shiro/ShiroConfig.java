package com.work.boot.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname ShiroConfig
 * @Description TODO
 * @Date 2020/6/2 23:25
 * @CreateComputer by PC
 * @Created by cxd
 */
@Configuration
public class ShiroConfig {

    @Bean
    public UserRealm userRealm() {// 自定义认证和授权的（领域类）
        return new UserRealm();
    }

    /**
     * shiro的生命周期管理,可以自定义配置
     * 其实这个类在shiro的自动注入里边是配置了的
     *
     * @return
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {// 控制器注解的使用需要配置
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    /**
     * 配置缓存管理器，单机版的用ecache组件，集群版用Redis,其实单机版也可以用redis
     *
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager() {// 2、缓存配置
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }

    /**
     * 可以自定义  SessionId 生成器 给SessionDAO用的
     *
     * @return
     */
    @Bean
    public SessionIdGenerator sessionIdGenerator() { // 默认就是一个UUID的生成器，但是你可以自定义
        return new JavaUuidSessionIdGenerator();
    }

    /**
     * 对ShiSession的状态的增删改查
     *
     * @param sessionIdGenerator
     * @return
     */
    @Bean
    public SessionDAO sessionDAO(SessionIdGenerator sessionIdGenerator) { // 4
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();// 企业级缓存绘画sessionDao
        // 配置Shiro的名字，实际上没有多大个作用
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDAO.setSessionIdGenerator(sessionIdGenerator);
        return sessionDAO;
    }

    // 配置记住我的功能

    @Bean
    public RememberMeManager rememberMeManager() { // 5
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        SimpleCookie cookie = new SimpleCookie("bs-RememberMe");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(7 * 24 * 60 * 60 * 1000); // 单位是秒，cookie存活的时间，如果存活7天
        rememberMeManager.setCookie(cookie);
        return rememberMeManager;
    }

    /**
     * 开启线程去循环检测Session的状态的
     * 建议大家用这个
     * 其实这里有两种方式配置session过期检测，建议用这种，但是也可以不配置
     *
     * @return
     */
    @Bean
    public ExecutorServiceSessionValidationScheduler executorServiceSessionValidationScheduler(DefaultWebSessionManager defaultWebSessionManager) {
        ExecutorServiceSessionValidationScheduler sessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();
//        sessionValidationScheduler.setInterval(30 * 60 * 1000);// 设置循环检测的时间
        sessionValidationScheduler.setInterval(30 * 60 * 1000);// 设置循环检测的时间
        sessionValidationScheduler.setSessionManager(defaultWebSessionManager);
        sessionValidationScheduler.enableSessionValidation();// 开启session检测
        return sessionValidationScheduler;
    }

    /**
     * 指的是ShiSession的会话管理员，它去管理者ShiroSession状态
     *
     * @param sessionDAO
     * @return
     */
    @Bean
    public DefaultWebSessionManager defaultWebSessionManager(SessionDAO sessionDAO) { // 6
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(30 * 60 * 1000);// 设置Session全局过期时间
        sessionManager.setDeleteInvalidSessions(true);// 如果检测到过期就删除Session
        sessionManager.setSessionValidationSchedulerEnabled(true);// 开启循环检测
        sessionManager.setSessionDAO(sessionDAO);// 设置SessionDAO
        SimpleCookie sessionIdCookie = new SimpleCookie("qf-java-session-id");//设置一个SessionCookie
        sessionIdCookie.setHttpOnly(true);// 仅支持Http

        sessionIdCookie.setMaxAge(-1);// -1永不过期，0表示删除

        sessionManager.setSessionIdCookie(sessionIdCookie);

        sessionManager.setSessionIdUrlRewritingEnabled(false);// 不把SessionId放到浏览器地址栏中

        sessionManager.setSessionIdCookieEnabled(true);// 开启SessCookie
        return sessionManager;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(Realm userRealm, EhCacheManager cacheManager,
                                                     SessionManager sessionManager, RememberMeManager rememberMeManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setCacheManager(cacheManager);
        securityManager.setSessionManager(sessionManager);
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }


    /**
     * 对Shiro的配置进行切面管理
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }


//        //    自定义登陆过滤器
//    public FormAuthenticationFilter getLoginFilter() { // 在ShiroFilterFactoryBean中使用
//        FormAuthenticationFilter filter = new FormAuthenticationFilter();// 这种方式 是使用shiro自带的过滤器，如果说要达到自己想要的效果
//
//        filter.setUsernameParam("phone");
//        filter.setPasswordParam("password");
//        filter.setRememberMeParam("rememberMe");
//        filter.setLoginUrl("/login");    // 设置登录的接口
//        filter.setFailureKeyAttribute("error"); // 配置错误消息
//        return filter;
//    }

    //    自定义注销登录之后做的事情
    public LogoutFilter getLogoutFilter() { // 在ShiroFilterFactoryBean中使用
        LogoutFilter logoutFilter = new LogoutFilter();// 退出登录过滤器做了点手脚
        logoutFilter.setRedirectUrl("/home/loginPage");    //  自定义退出登录之后，跳转访问的方法路径
        return logoutFilter;
    }

    /**
     * [掌握！]这个是配置拦截路径的一个类
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 1：必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/home/loginPage");    // 登陆错误或者被拦截之后，去访问访问的路径（方法）而不是页面
        shiroFilterFactoryBean.setSuccessUrl("/pages/back/loginSuccess");    // 设置登陆成功执行方法的路径

//        shiroFilterFactoryBean.setUnauthorizedUrl("/unAuthPage");  // 没有授权，后执行的方法，在控制器上的注解没有授权，没有角色或者权限，抛出异常之后，跳转的地址，这个配置无效的，需要全局异常捕获

        Map<String, Filter> filters = new HashMap<>();// map的key就是过滤器的名字
        // 增加自定义过滤器
//        filters.put("authc", this.getLoginFilter());
        filters.put("logout", this.getLogoutFilter());
        // 把这些过滤器交给Shiro控制，就会覆盖它自带的过滤器， 采用你配置的过滤器
        shiroFilterFactoryBean.setFilters(filters);
        // 这里其实 分为上下两部分，上面部分是配置过滤器的，也就是说有哪些过滤器形成的链条


        // 配置哪个过滤器过滤哪个路径，而且顺序是从上往下匹配
        Map<String, String> filterChainDefinitionMap = new HashMap<>();
//        配置拦截路径用的，也就是说哪些路径应该经过哪些过滤器处理
//        key:指的是路径
//        value:指的是过滤器的名称简写
        filterChainDefinitionMap.put("/logout", "logout");

//        filterChainDefinitionMap.put("/login", "user");    // 定义内置登录处理,表单需要加method=post

        filterChainDefinitionMap.put("/home/main", "authc");// 这些路径全部需要登录之后，才能访问
        filterChainDefinitionMap.put("/user/**", "authc");// 这些路径全部需要登录之后，才能访问
        filterChainDefinitionMap.put("/admin/**", "authc");// 这些路径全部需要登录之后，才能访问
        filterChainDefinitionMap.put("/messages/**", "authc");// 这些路径全部需要登录之后，才能访问


        filterChainDefinitionMap.put("/**", "anon");// 所有路径不需要任何检测，即可访问

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 配合thymeleaf使用shiro标签的，与上面无关。
     *
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {// 页面这样就支持shiro标签了，就可以使用shiro标签进行对角色和权限的检测
        return new ShiroDialect();
    }


}
