package com.zhang.service;

import com.zhang.bean.Lyric;
import com.zhang.mapper.LyricMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional(isolation = Isolation.READ_COMMITTED)
@Service
public class LyricService {
    @Resource
    LyricMapper lyricMapper;

    public Lyric getLyricBySongId(Integer song_id){
        return lyricMapper.getLyricBySongId(song_id);
    }
}
