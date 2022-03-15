package com.zhang.controller;

import com.zhang.bean.AuthConstant;
import com.zhang.bean.R;
import com.zhang.bean.Song;
import com.zhang.service.SongService;
import com.zhang.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 歌曲控制类
 */
@RestController
public class SongController {
    @Resource
    SongService songService;


    /**
     * 获取一页歌曲
     * @param start 页数
     * @param offset 每页数量
     * @return R
     */
    @GetMapping("/song")
    public R getOnePageSong(@RequestParam Integer start, @RequestParam Integer offset){
        List<Song> SongList = songService.getOnePageSong(start, offset);
        return new R(200, AuthConstant.SUCCESS, SongList);
    }

    /**
     * 根据标签获取一页歌曲
     * @param tag 标签
     * @param start 页数
     * @param offset 每页数量
     * @return R
     */
    @GetMapping("/songByTag/{tag}")
    public R getOnePageSongByTag(@PathVariable String tag, @RequestParam Integer start, @RequestParam Integer offset){
        List<Song> SongList = songService.getOnePageSongByTag(start, offset, tag);
        return new R(200, AuthConstant.SUCCESS, SongList);
    }

    /**
     * 模糊搜索
     * @param name 歌名
     * @param start 页数
     * @param offset 每页数量
     * @return R
     */
    @GetMapping("/songByName/{name}")
    public R getOnePageSongByName(@PathVariable String name, @RequestParam Integer start, @RequestParam Integer offset){
        List<Song> SongList = songService.getOnePageSongByName(start, offset, name);
        return new R(200, AuthConstant.SUCCESS, SongList);
    }

    /**
     * 收藏歌曲
     * @param userId 用户ID
     * @param songId 歌曲ID
     * @return R
     */
    @PutMapping("/loveSong/{userId}/{songId}")
    public R loveSong(@PathVariable Integer userId, @PathVariable String songId){
        songService.loveSong(userId, songId);
        return new R(200, AuthConstant.SUCCESS, null);
    }
}
