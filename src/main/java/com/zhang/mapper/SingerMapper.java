package com.zhang.mapper;

import com.zhang.bean.Singer;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@CacheNamespace
@Mapper
public interface SingerMapper {
    @Select("select * from singer limit #{start}, #{offset}")
    public List<Singer> getOnePageSinger(Integer start, Integer offset);
    @Select("select * from singer where f_py = #{f_py} limit #{start}, #{offset}")
    public List<Singer> getOnePageSingerByPinYin(String f_py, Integer start, Integer offset);

    @Select("select * from singer where singer_name like #{singer_name} limit #{start}, #{offset}")
    public List<Singer> getOnePageSingerByNameFuzzily(String singer_name, Integer start, Integer offset);

    @Select("select * from singer where singer_id = #{singer_id}")
    public Singer getSingerById(Integer singer_id);
}
