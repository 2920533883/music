package com.zhang.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhang.bean.AuthConstant;
import com.zhang.bean.R;
import com.zhang.bean.User;
import com.zhang.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 用户控制类
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 修改密码
     *
     * @param param    改密码所需要的信息：用户名、旧密码、新密码
     * @param response 响应头
     * @return R
     */
    @PutMapping("/user/password")
    public R updatePassword(@RequestBody JSONObject param, ServletResponse response) {
        String username = (String) param.get("username");
        String oldPassword = (String) param.get("oldpassword");
        String newPassword = (String) param.get("newpassword");
        return userService.updateUserPassword(username, oldPassword, newPassword, response);
    }

    /**
     * 注册
     *
     * @param user 待注册的用户信息
     * @return R
     */
    @PostMapping("/user/register")
    public R signup(@RequestBody User user) {
        return userService.register(user);
    }

    /**
     * 登录
     *
     * @param user     待登录的用户信息
     * @param response // 响应头
     * @return R
     */
    @PostMapping("/user/login")
    public R login(@RequestBody User user, ServletResponse response) {
        return userService.login(user, response);
    }

    /**
     * 根据Token获取用户信息
     *
     * @param token Token
     * @return R
     */
    @GetMapping("/usert")
    public R getUserByToken(@RequestParam("token") String token) {
        return new R(200, "获取成功！", userService.selectUserByToken(token));
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return R
     */
    @GetMapping("/useru")
    public R getUserByUsername(@RequestParam("username") String username) {
        User user = userService.selectUserByUsername(username);
        return new R(200, "获取成功！", user);
    }

    /**
     * 按昵称模糊搜索
     *
     * @param name 用户名
     * @return R
     */
    @GetMapping("/useru/fuzzily")
    public R getUserByUsernameFuzzily(@RequestParam("name") String name) {
        return new R(200, "获取成功！", userService.selectUserByNameFuzzily(name));
    }

    /**
     * 获取所有用户信息
     *
     * @return R
     */
    @GetMapping("/user/all")
    public R getAllUser() {
        return new R(200, "获取成功！", userService.selectAllUser());
    }

    /**
     * 按ID更新用户信息
     * @param user 用户信息
     * @return R
     */
    @PutMapping("/user")
    public R updateUserById(@RequestBody User user) {
        userService.updateUserInfo(user);
        return new R(200, "修改成功！", user);
    }


    /**
     * 注销
     *
     * @param request  请求
     * @param response 响应
     * @return R
     */
    @PostMapping("/user/logout")
    public R logout(ServletRequest request, ServletResponse response) {
        return userService.logout(request, response);
    }

    /**
     * 更新用户头像
     * @param id 用户ID
     * @return R
     */
    @PostMapping("/user/icon/{id}")
    public R updateIconById(@RequestParam("file") MultipartFile file, @PathVariable String id) throws IOException {
        return new R(200, AuthConstant.SUCCESS, userService.uploadIcon(file, id));
    }
}
