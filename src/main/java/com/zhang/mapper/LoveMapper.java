package com.zhang.mapper;

import com.zhang.bean.Love;
import org.apache.ibatis.annotations.*;

import java.util.List;

@CacheNamespace
@Mapper
public interface LoveMapper {

    @Select("select * from love where user_id = #{user_id} order by create_time desc limit #{start}, #{offset}")
    List<Love> getLoveByUserId(Integer user_id, Integer start, Integer offset);


    @Select("select count(*) as total from love where user_id = #{user_id}")
    int getLoveTotal(Integer user_id);


    @Select("select * from love where user_id = #{user_id} and song_id = #{song_id}")
    Love checkIfLove(Integer user_id, Integer song_id);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into love value (null, #{user_id}, #{song_id}, #{create_time})")
    void insertLove(Love love);

    @Insert("Delete from love where user_id = #{user_id} and song_id = #{song_id}")
    void deleteLove(Integer user_id, Integer song_id);
}
