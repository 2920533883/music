package com.zhang.mapper;

import com.zhang.bean.Follow;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@CacheNamespace
@Mapper
public interface FollowMapper {

    @Select("select * from follow where user_id = #{user_id}")
    List<Follow> getFollowing(Integer user_id);

    @Select("select * from follow where following = #{user_id}")
    List<Follow> getFollower(Integer user_id);

    @Delete("delete from follow where user_id = #{user_id} and following = #{following}")
    void deleteFollow(Follow follow);

    @Select("select * from follow where user_id = #{user_id} and following = #{following}")
    Follow checkIfFollow(Follow follow);

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into follow value (null, #{user_id}, #{following}, #{create_time})")
    void insertFollow(Follow follow);
}
