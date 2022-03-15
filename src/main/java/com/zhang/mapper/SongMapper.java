package com.zhang.mapper;

import com.zhang.bean.Song;
import com.zhang.bean.Tag;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@CacheNamespace
@Mapper
public interface SongMapper {

    @Select({"select * from song limit #{start}, #{offset}"})
    List<Song> getOnePageSong(Integer start, Integer offset);

    @Select({"select * from song where song_tag like #{tag} limit #{start}, #{offset}"})
    List<Song> getOnePageSongByTag(Integer start, Integer offset, String tag);

    @Select({"select * from song where song_name like #{song_name} limit #{start}, #{offset}"})
    List<Song> getOnePageSongByName(Integer start, Integer offset, String song_name);
}
