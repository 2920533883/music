package com.zhang.mapper;

import com.zhang.bean.Song;
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

    @Select("select count(*) as total from song where song_tag like #{tag}")
    int getSongByTagTotal(String tag);

    @Select({"select * from song where song_name like #{song_name} limit #{start}, #{offset}"})
    List<Song> getOnePageSongByName(Integer start, Integer offset, String song_name);

    @Select({"select * from song where song_id = #{song_id}"})
    Song getSongById(Integer song_id);

    @Select({"select * from song where album_id = #{album_id}"})
    List<Song> getSongByAlbumId(Integer album_id);
}
