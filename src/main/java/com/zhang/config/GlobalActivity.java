package com.zhang.config;

import org.springframework.stereotype.Component;
import javax.annotation.PreDestroy;


/**
 * 应用停止前对热点数据持久化
 */
@Component
public class GlobalActivity {

    @PreDestroy
    public void destroy() throws Exception {

    }
}
