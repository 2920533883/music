package com.zhang.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

// 歌曲实体
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Song implements Serializable {
    Integer song_id;
    String song_name;
    String song_url;
    Integer song_time;
    String singer_list;
    Integer album_id;
    String song_tag;
    Integer click_num;
    Integer share_num;
    Integer love_num;
}
