package com.zhang.mapper;

import com.zhang.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserMapper {
    // 更新照片url
    @Update({"update user set icon_url=#{icon_url} where user_id=#{user_id}"})
    void updateIcon(String icon_url, String user_id);

    // 更新用户信息
    @Update({"update user set username=#{username}, birthday=#{birthday},address=#{address}, gender=#{gender} where user_id=#{user_id}"})
    void updateUserInfo(User user);

    // 更新密码
    @Update({"update user set password=#{password} where username=#{username}"})
    void updateUserPassword(String username, String password);


    // 按用户名精确查询用户
    @Select({"SELECT * FROM `user` WHERE `username` = #{username}"})
    User selectUserByUsername(String username);

    // 按昵称模糊查询用户
    @Select({"SELECT * FROM `user` WHERE `name` like #{name}"})
    List<User> selectUserByNameFuzzily(String name);

    // 获取所有用户
    @Select({"select * from user"})
    List<User> selectAllUser();

    // 插入用户
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert({"insert into user values(#{user_id}, #{name}, #{username}, #{password}, #{profession}, #{create_time}, #{icon_url}, #{love_song}, #{love_tag}, #{following}, #{follower}, #{birthday}, #{address}, #{gender})"})
    void insertUser(User user);

    // 获取收藏歌曲
    @Select("select love_song from user where user_id = #{user_id}")
    String getLoveSongByUserId(Integer user_id);


    // 更新收藏歌曲
    @Update("update user set love_song = #{love_song} where user_id = #{user_id}")
    void updateLoveSongByUserId(Integer user_id, String love_song);
}
