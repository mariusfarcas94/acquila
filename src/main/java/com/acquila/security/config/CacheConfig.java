package com.acquila.security.config;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;

/**
 * This class provides a minimal configuration for Hazelcast in-memory cache.
 */
@Configuration
@EnableCaching
@Profile("!test")
public class CacheConfig extends CachingConfigurerSupport {

    private final Log logger = LogFactory.getLog(CacheConfig.class);

    @Value("${cache.config.file}")
    private Resource hazelcastConfigPath;

    @Value("${cache.group.username}")
    private String hazelcastGroupUsername;

    @Value("${cache.group.password}")
    private String hazelcastGroupPassword;


    @Bean
    public CacheManager cacheManager() {
        return new HazelcastCacheManager(hazelcastInstance());
    }

    @Bean
    public HazelcastInstance hazelcastInstance() {

        Config cfg;

        try {
            cfg = new XmlConfigBuilder(hazelcastConfigPath.getInputStream()).build();

        } catch (IOException e) {
            logger.fatal("Failed to load Hazelcast config. Switching to a default configuration.", e);

            cfg = new Config();

            cfg.getGroupConfig()
                    .setName(hazelcastGroupUsername)
                    .setPassword(hazelcastGroupPassword);

            NetworkConfig network = cfg.getNetworkConfig();
            JoinConfig join = network.getJoin();
            join.getMulticastConfig().setEnabled(false);
        }

        cfg.setInstanceName("authServiceInstance");

        return Hazelcast.newHazelcastInstance(cfg);
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return (targetInstance, methodName, parameters) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(targetInstance.getClass().getName());
            sb.append(methodName.getName());
            for (Object param : parameters) {
                if (param != null) {
                    sb.append(param.toString());
                }
            }
            return sb.toString();
        };
    }
}
