package com.zhang.mapper;

import com.zhang.bean.Lyric;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@CacheNamespace
@Mapper
public interface LyricMapper {

    @Select("select * from lyric where song_id = #{song_id}")
    Lyric getLyricBySongId(Integer song_id);

}
