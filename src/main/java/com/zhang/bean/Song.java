package com.zhang.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhang.handler.List2VarcharHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

// 歌曲实体
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(autoResultMap = true)
public class Song implements Serializable {
    Integer song_id;
    String song_name;
    @TableField(typeHandler = List2VarcharHandler.class)
    List<String> singer_name;
    Integer song_time;
    @TableField(typeHandler = List2VarcharHandler.class)
    List<String> singer_list;
    String album_name;
    Integer album_id;
    @TableField(typeHandler = List2VarcharHandler.class)
    List<String> song_tag;
    Integer play_num;
    Integer love_num;
}
