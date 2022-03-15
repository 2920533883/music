package com.zhang.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

// 歌词实体
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Lyric implements Serializable {
    Integer lyric_id;
    Integer song_id;
    String lyric;
    String t_lyric;
}
