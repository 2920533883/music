package com.zhang.mapper;

import com.zhang.bean.Song;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@CacheNamespace
@Mapper
public interface SongMapper {

    @Select({"select * from song order by play_num desc limit 0, 15"})
    List<Song> getHotSong();

    @Select({"select * from song where song_tag like #{tag} order by play_num desc limit #{start}, #{offset}"})
    List<Song> getOnePageSongByTag(Integer start, Integer offset, String tag);

    @Select("select count(*) as total from song where song_tag like #{tag}")
    int getSongByTagTotal(String tag);

    @Select({"select * from song where song_name like #{song_name} order by play_num desc"})
    List<Song> getSongByName(String song_name);

    @Select({"select * from song where song_id = #{song_id}"})
    Song getSongById(Integer song_id);

    @Select({"select * from song where album_id = #{album_id}"})
    List<Song> getSongByAlbumId(Integer album_id);
}
