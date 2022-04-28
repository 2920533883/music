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
    @Update({"update user set name=#{name}, profession=#{profession}, birthday=#{birthday}, gender=#{gender}, love_tag=#{love_tag} where user_id=#{user_id}"})
    void updateUserInfo(User user);

    // 更新密码
    @Update({"update user set password=#{password} where username=#{username}"})
    void updateUserPassword(String username, String password);


    // 按用户名精确查询用户
    @Select({"SELECT * FROM `user` WHERE `username` = #{username}"})
    User selectUserByUsername(String username);

    // 按用户ID精确查询用户
    @Select({"SELECT * FROM `user` WHERE `user_id` = #{user_id}"})
    User selectUserByID(Integer user_id);


    // 按昵称模糊查询用户
    @Select({"SELECT * FROM `user` WHERE `name` like #{name}"})
    List<User> selectUserByNameFuzzily(String name);

    // 获取所有用户
    @Select({"select * from user"})
    List<User> selectAllUser();

    // 插入用户
    @Options(useGeneratedKeys = true, keyProperty = "user_id", keyColumn = "user_id")
    @Insert({"insert into user values(null, #{name}, #{username}, #{password}, null, #{create_time}, 'https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png', null, -1, '[]')"})
    void insertUser(User user);


    // 获取用户简易信息
    @Select("select user_id, name, icon_url from user where user_id = #{user_id}")
    User getSimpleInfo(Integer user_id);

}
