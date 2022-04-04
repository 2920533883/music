package com.zhang.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhang.handler.List2VarcharHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

// 用户实体
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@TableName(autoResultMap = true)
public class User implements Serializable {
    Integer user_id;
    String name;
    String username;
    String password;
    Integer profession;
    String create_time;
    String icon_url;
    String birthday;
    String address;
    Integer gender;
    @TableField(typeHandler = List2VarcharHandler.class)
    List<String> love_song;
    @TableField(typeHandler = List2VarcharHandler.class)
    List<String> love_tag;
    @TableField(typeHandler = List2VarcharHandler.class)
    List<String> following;
    @TableField(typeHandler = List2VarcharHandler.class)
    List<String> follower;
    @TableField(typeHandler = List2VarcharHandler.class)
    List<String> play_history;
}
