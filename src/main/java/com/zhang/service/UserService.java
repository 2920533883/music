package com.zhang.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.zhang.bean.AuthConstant;
import com.zhang.bean.Follow;
import com.zhang.bean.R;
import com.zhang.bean.User;
import com.zhang.mapper.*;
import com.zhang.util.JwtUtil;
import com.zhang.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional(isolation = Isolation.READ_COMMITTED)
@Service
public class UserService {
    @Resource
    UserMapper userMapper;

    @Resource
    TagMapper tagMapper;

    @Resource
    FollowMapper followMapper;

    @Resource
    PlayMapper playMapper;

    @Resource
    SongMapper songMapper;
    @Value("${aliyun.oss.endpoint}")
    private String ALIYUN_OSS_ENDPOINT;
    @Value("${aliyun.oss.accessKeyId}")
    private String ALIYUN_OSS_ACCESSKEYID;
    @Value("${aliyun.oss.accessKeySecret}")
    private String ALIYUN_OSS_ACCESSKEYSECRET;
    @Value("${aliyun.oss.bucketName}")
    private String ALIYUN_OSS_BUCKETNAME;

    public String uploadIcon(MultipartFile file, String id) throws IOException {
        OSSClient ossClient = new OSSClient(ALIYUN_OSS_ENDPOINT, ALIYUN_OSS_ACCESSKEYID, ALIYUN_OSS_ACCESSKEYSECRET);
        String objectName = "user-" + id;
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("image/jpg");
        ossClient.putObject(ALIYUN_OSS_BUCKETNAME, objectName, new ByteArrayInputStream(file.getBytes()), objectMetadata);
        Date expiration = new Date(System.currentTimeMillis() + (long) 3600 * 10000000);
        String url = ossClient.generatePresignedUrl(ALIYUN_OSS_BUCKETNAME, objectName, expiration).toString();
        ossClient.shutdown();
        userMapper.updateIcon(url, id);
        return url;
    }

    public void updateUserInfo(User user) {
        userMapper.updateUserInfo(user);
    }

    public R updateUserPassword(String username, String oldpassword, String newpassword, ServletResponse response) {
        User user = userMapper.selectUserByUsername(username);
        if (SecurityUtil.getSHA256(oldpassword).equals(user.getPassword())) {
            deleteToken(user);
            JwtUtil.editCookieToken(response, "");
            userMapper.updateUserPassword(username, SecurityUtil.getSHA256(newpassword));
            return new R(200, AuthConstant.SUCCESS, null);
        } else return new R(400, AuthConstant.WRONG_PASSWORD, null);
    }

    public User selectUserByToken(String token) {
        final String username = JwtUtil.getUsername(token);
        return userMapper.selectUserByUsername(username);
    }

    public User selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    public Map<String, Object> selectUserByID(Integer id) {
        Map<String, Object> res = new HashMap<>();
        User user = userMapper.selectUserByID(id);
        if (user == null) return null;
        // 关注我的
        List<Follow> followerList = followMapper.getFollower(user.getUser_id());
        List<User> resFollower = new ArrayList<>();
        followerList.forEach(follower -> {
            User simpleInfo = userMapper.getSimpleInfo(follower.getUser_id());
            resFollower.add(simpleInfo);
        });
        // 我关注的
        List<Follow> followingList = followMapper.getFollowing(user.getUser_id());
        List<User> resFollowing = new ArrayList<>();
        followingList.forEach(following -> {
            User simpleInfo = userMapper.getSimpleInfo(following.getFollowing());
            resFollowing.add(simpleInfo);
        });
        List<String> resTag = user.getLove_tag();
        res.put("user", user);
        res.put("follower", resFollower);
        res.put("following", resFollowing);
        res.put("loveTag", resTag);
        return res;
    }

    public List<User> selectUserByNameFuzzily(String name) {
        return userMapper.selectUserByNameFuzzily("%" + name + "%");
    }

    public List<User> selectAllUser() {
        return userMapper.selectAllUser();
    }

    public R logout(ServletRequest request, ServletResponse response) {
        String token = JwtUtil.getRequestToken((HttpServletRequest) request);
        String username = JwtUtil.getUsername(token);
        User user = userMapper.selectUserByUsername(username);
        deleteToken(user);
        JwtUtil.editCookieToken(response, "");
        return new R(200, AuthConstant.SUCCESS, null);
    }

    public R register(User user) {
        R response;
        User checkUser = userMapper.selectUserByUsername(user.getUsername());
        if (checkUser == null) { //用户名未注册
            user.setPassword(SecurityUtil.getSHA256(user.getPassword()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            user.setCreate_time(sdf.format(new Date()));
            userMapper.insertUser(user);
            response = new R(200, AuthConstant.SUCCESS, user);
        } else {
            response = new R(400, AuthConstant.ALREADY_REG, null);
        }
        return response;
    }

    public void deleteToken(User user) {
        Date currentTime = new Date();
        JwtUtil.sign(user, currentTime);
    }

    public R login(User user, ServletResponse response) {
        R resp;
        User checkUser = userMapper.selectUserByUsername(user.getUsername());
        if (checkUser != null) { // 用户存在
            if (SecurityUtil.getSHA256(user.getPassword()).equals(checkUser.getPassword())) {
                Date setTime = new Date();
                Date expireTime = new Date();
                expireTime.setTime(setTime.getTime() + AuthConstant.EXPIRE_TIME);
                String token = JwtUtil.sign(checkUser, expireTime);
                JwtUtil.editCookieToken(response, token);
                User simpleInfo = userMapper.getSimpleInfo(checkUser.getUser_id());
                resp = new R(200, AuthConstant.SUCCESS, simpleInfo);
            } else {
                resp = new R(400, AuthConstant.WRONG_PASSWORD, null);
            }
        } else {//用户不存在
            resp = new R(400, AuthConstant.UNKNOWN_ACCOUNT, null);
        }
        return resp;
    }

    public boolean follow(Integer user_id, Integer following) {
        Follow follow = new Follow();
        follow.setFollowing(following);
        follow.setUser_id(user_id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        follow.setCreate_time(sdf.format(new Date()));
        if (followMapper.checkIfFollow(follow) == null) {
            followMapper.insertFollow(follow);
            return true;
        }
        return false;
    }

    public void unFollow(Integer user_id, Integer following) {
        Follow follow = new Follow();
        follow.setFollowing(following);
        follow.setUser_id(user_id);
        followMapper.deleteFollow(follow);
    }

    public Follow getFollow(Integer user_id, Integer following) {
        Follow follow = new Follow();
        follow.setFollowing(following);
        follow.setUser_id(user_id);
        return followMapper.checkIfFollow(follow);
    }
}
