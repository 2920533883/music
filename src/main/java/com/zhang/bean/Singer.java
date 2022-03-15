package com.zhang.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

// 歌手实体
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Singer implements Serializable {
    Integer singer_id;
    String singer_name;
    String singer_img;
    Integer singer_type;
    String album_list;
}
