package com.zhang.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhang.handler.List2VarcharHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

// 歌手实体
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@TableName(autoResultMap = true)
public class Singer implements Serializable {
    Integer singer_id;
    String singer_name;
    String singer_img;
    Integer singer_type;
    @TableField(typeHandler = List2VarcharHandler.class)
    String album_list;
}
