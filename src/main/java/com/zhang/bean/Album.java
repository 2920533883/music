package com.zhang.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

// 专辑实体
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Album implements Serializable {
    Integer album_id;
    String album_name;
    String album_img;
    String album_publish_time;
    String singer_list;
    String song_list;
    Integer click_num;
    Integer share_num;
}
