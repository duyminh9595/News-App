package com.dongok.hello.config;

import com.dongok.hello.entity.NewsInfo;
import com.dongok.hello.entity.NewsType;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Component
public class RestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(NewsInfo.class);
        config.exposeIdsFor(NewsType.class);
    }
}