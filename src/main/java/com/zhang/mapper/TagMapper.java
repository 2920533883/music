package com.zhang.mapper;

import com.zhang.bean.Tag;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@CacheNamespace
@Mapper
public interface TagMapper {


    @Select({"select * from tag where father_tag = #{father_tag}"})
    List<Tag> getTagsByFid(Integer father_tag);

    @Select({"select * from tag where tag_id = #{tag_id}"})
    Tag getTagsByID(String tag_id);
}
