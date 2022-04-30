package com.zhang.mapper;

import com.zhang.bean.Song;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Update("update song set play_num = #{play_num} where song_id = #{song_id}")
    void updatePlayNum(Integer song_id, Integer play_num);

    @Update("update song set love_num = #{love_num} where song_id = #{song_id}")
    void updateLoveNum(Integer song_id, Integer love_num);

    @Select("SELECT * FROM zs_music.song AS t1 JOIN (SELECT ROUND(RAND() * (SELECT MAX(song_id) FROM zs_music.song)) AS song_id) AS t2 WHERE t1.song_id >= t2.song_id ORDER BY t1.song_id ASC LIMIT 1000")
    List<Song> getRandomSong();

}
