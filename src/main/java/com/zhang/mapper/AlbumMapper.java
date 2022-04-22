package com.zhang.mapper;

import com.zhang.bean.Album;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@CacheNamespace
@Mapper
public interface AlbumMapper {


    @Select("select * from album where album_id = #{album_id}")
    Album getAlbumById(Integer album_id);

    @Select("select * from album where album_name like #{album_name} order by click_num desc")
    List<Album> getAlbumByNameFuzzily(String album_name);

    @Select("select count(*) as total from album where album_name = #{album_name}")
    int getAlbumByNameFuzzilyTotal(String album_name);

    @Select("select * from album where singer_list like #{singer_id} order by album_publish_time desc limit #{start}, #{offset}")
    List<Album> getAlbumBySingerId(String singer_id, Integer start, Integer offset);

    @Select("select count(*) as total from album where singer_list like #{singer_id}")
    List<Album> getAlbumBySingerIdTotal(String singer_id);

    @Update("update album set click_num = click_num + 1")
    void addClickNum();

}
