package com.zhang.controller;

import com.zhang.bean.AuthConstant;
import com.zhang.bean.R;
import com.zhang.service.LyricService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

@RestController
public class LyricController {

    @Resource
    LyricService lyricService;

    /**
     * 获取歌曲歌词
     * @param song_id 歌曲ID
     * @return R
     */
    @GetMapping("/lyric/{song_id}")
    public R getLyric(@PathVariable Integer song_id){
        return new R(200, AuthConstant.SUCCESS, lyricService.getLyricBySongId(song_id));
    }

}
