package org.springframework.samples.petclinic;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.samples.petclinic.util.CallMonitoringAspect;

@Configuration
@EnableAspectJAutoProxy
@EnableMBeanExport
@EnableCaching
public class ToolsConfig {

    @Bean
    public CallMonitoringAspect callMonitor() {
        return new CallMonitoringAspect();
    }

    @Bean
    public CacheManager cacheManager(net.sf.ehcache.CacheManager cacheManager) {
        return new EhCacheCacheManager(cacheManager);
    }

    @Bean
    public EhCacheManagerFactoryBean ehcache() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("cache/ehcache.xml"));
        return ehCacheManagerFactoryBean;
    }
}
