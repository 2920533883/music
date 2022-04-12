package com.zhang.mapper;

import com.zhang.bean.Follow;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@CacheNamespace
@Mapper
public interface FollowMapper {

    @Select("select * from follow where user_id = #{user_id}")
    List<Follow> getFollowing(Integer user_id);

    @Select("select * from follow where following = #{user_id}")
    List<Follow> getFollower(Integer user_id);
}
