package com.zhang.config;

import com.zhang.bean.Song;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 注册自定义容器
 */
@Configuration
public class MyConfig {
//    @Resource
//    AuthService authService;
//    HashMap<String, Integer> hotSearchMap = new HashMap<String, Integer>();
//    HashMap<String, Integer> hotClickMap = new HashMap<String, Integer>();
//    HashMap<String, List<String>> authMap = new HashMap<String, List<String>>();
//    @Value("${file.path}")
//    private String filePath;
//
//    @Bean("HotSearchMap")
//    public HashMap<String, Integer> getHotSearchMap() throws Exception {
//        BufferedReader br = new BufferedReader(new FileReader(filePath+"hotSearch.txt"));
//        String line;
//        while ((line = br.readLine()) != null){
//            String[] s = line.split(" ");
//            hotSearchMap.put(s[0], Integer.valueOf(s[1]));
//        }
//        return hotSearchMap;
//    }
//
//    @Bean("AuthMap")
//    public HashMap<String, List<String>> getAuthMap() {
//        List<String> adminAuth = new ArrayList<>();
//        List<Auth> adminAuthList = authService.getAuthByRoleId(2);
//        adminAuthList.forEach(auth -> {
//            adminAuth.add(auth.getAuthName());
//        });
//        authMap.put("管理员", adminAuth);
//        List<String> superAdminAuth = new ArrayList<>();
//        List<Auth> superAdminAuthList = authService.getAuthByRoleId(1);
//        superAdminAuthList.forEach(auth -> {
//            superAdminAuth.add(auth.getAuthName());
//        });
//        authMap.put("超级管理员", superAdminAuth);
//        return authMap;
//    }
//
}
