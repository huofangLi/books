package com.books.server.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

/**
 * @author zhaoyuan
 */
@Configuration
@AutoConfigureAfter(ApplicationProperties.class)
public class AliyunOSSConfig {

    private final Logger log = LoggerFactory.getLogger(AliyunOSSConfig.class);

    private OSS ossClient = null;

    @Autowired
    private ApplicationProperties applicationProper;

    @Bean
    public OSS ossClient() {
        ossClient = new OSSClientBuilder().build(applicationProper.getOss().getEndpoint(),
                applicationProper.getOss().getKey(),
                applicationProper.getOss().getSecret());
        return ossClient;
    }

    @PreDestroy
    public void shutdown() {
        log.info("destroy oss client");
        if (ossClient != null) {
            ossClient.shutdown();
        }
    }
}
