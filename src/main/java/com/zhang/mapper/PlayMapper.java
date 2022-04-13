package com.zhang.mapper;

import com.zhang.bean.Follow;
import com.zhang.bean.Play;
import org.apache.ibatis.annotations.*;

import java.util.List;

@CacheNamespace
@Mapper
public interface PlayMapper {

    @Select("select * from play where user_id = #{user_id} order by play_num desc limit 0, 20")
    List<Play> getPlayHistory(Integer user_id);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into play value (null, #{user_id}, #{song_id}, 1)")
    void addPlayHistory(Integer user_id, Integer song_id);

    @Select("select * from play where user_id = #{user_id} and song_id = #{song_id}")
    Play checkPlayHistory(Integer user_id, Integer song_id);

    @Update("update play set play_num = play_num+1 where user_id = #{user_id} and song_id = #{song_id}")
    void updatePlayHistory(Integer user_id, Integer song_id);
}
