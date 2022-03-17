package com.zhang.mapper;

import com.zhang.bean.Album;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@CacheNamespace
@Mapper
public interface AlbumMapper {


    @Select("select * from album where album_id = #{album_id}")
    public Album getAlbumById(Integer album_id);

    @Select("select * from album where album_name = #{album_name} limit #{start}, #{offset}")
    public List<Album> getOnePageAlbumByNameFuzzily(String album_name, Integer start, Integer offset);

    @Select("select * from album where singer_list like #{singer_id} limit #{start}, #{offset}")
    public List<Album> getAlbumBySingerId(String singer_id, Integer start, Integer offset);


}
