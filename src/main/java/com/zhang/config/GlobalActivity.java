package com.zhang.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;


/**
 * 应用停止前对热点数据持久化
 */
@Component
public class GlobalActivity {

    @PreDestroy
    public void destroy() throws Exception {

    }
}
