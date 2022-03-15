package com.zhang.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zhang.bean.Song;
import com.zhang.mapper.SongMapper;
import com.zhang.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Transactional(isolation = Isolation.READ_COMMITTED)
@Service
public class SongService {

    @Resource
    SongMapper songMapper;

    @Resource
    UserMapper userMapper;

    public List<Song> getOnePageSong(Integer start, Integer offset){
        return songMapper.getOnePageSong(start, offset);
    }

    public List<Song> getOnePageSongByTag(Integer start, Integer offset, String tag){
        return songMapper.getOnePageSongByTag(start, offset, "%"+tag+"%");
    }

    public List<Song> getOnePageSongByName(Integer start, Integer offset, String name){
        return songMapper.getOnePageSongByName(start, offset, "%"+name+"%");
    }

    public void loveSong(Integer userId, String songId){
        String loveSong = userMapper.getLoveSongByUserId(userId);
        JSONArray array = JSON.parseArray(loveSong);
        array.add(songId);
        userMapper.updateLoveSongByUserId(userId, array.toString());
    }
}
