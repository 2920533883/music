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
    List<Singer> getOnePageSinger(Integer start, Integer offset);

    @Select("select * from singer where f_py = #{f_py}")
    List<Singer> getOnePageSingerByPinYin(String f_py);

    @Select("select * from singer where singer_name like #{singer_name}")
    List<Singer> getOnePageSingerByNameFuzzily(String singer_name);

    @Select("select * from singer where singer_id = #{singer_id}")
    Singer getSingerById(Integer singer_id);
}
