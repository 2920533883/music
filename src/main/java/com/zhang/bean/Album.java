package com.zhang.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhang.handler.List2VarcharHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

// 专辑实体
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@TableName(autoResultMap = true)
public class Album implements Serializable {
    Integer album_id;
    String album_name;
    String album_img;
    String album_publish_time;
    @TableField(typeHandler = List2VarcharHandler.class)
    String singer_list;
    @TableField(typeHandler = List2VarcharHandler.class)
    String song_list;
    Integer click_num;
    Integer share_num;
}
