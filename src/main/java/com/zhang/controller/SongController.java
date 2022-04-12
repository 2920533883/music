package com.zhang.controller;

import com.zhang.bean.AuthConstant;
import com.zhang.bean.R;
import com.zhang.bean.Song;
import com.zhang.service.SongService;
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
        return new R(200, AuthConstant.SUCCESS, songService.getOnePageSongByTag(start, offset, tag));
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
     * @param user_id 用户ID
     * @param song_id 歌曲ID
     * @return R
     */
    @PutMapping("/loveSong")
    public R loveSong(@RequestParam("user_id") Integer user_id, @RequestParam("song_id") Integer song_id){
        if (songService.loveSong(user_id, song_id)) return new R(200, AuthConstant.SUCCESS, null);
        else return new R(400, AuthConstant.ERROR, null);
    }

    /**
     * 获取用户收藏歌曲
     * @param userId 用户ID
     * @return R
     */
    @GetMapping("/loveSong/{userId}")
    public R getLoveSong(@PathVariable Integer userId, @RequestParam Integer start, @RequestParam Integer offset){
        return new R(200, AuthConstant.SUCCESS, songService.getLoveSong(userId, start, offset));
    }

    /**
     * 取消收藏歌曲
     * @param userId 用户ID
     * @param songId 歌曲ID
     * @return R
     */
    @DeleteMapping("/loveSong/{userId}/{songId}")
    public R getLoveSong(@PathVariable Integer userId, @PathVariable Integer songId){
        songService.deleteLoveSong(userId, songId);
        return new R(200, AuthConstant.SUCCESS, null);
    }

    /**
     * 获取专辑里的歌曲
     * @param albumId 专辑ID
     * @return R
     */
    @GetMapping("/song/album/{albumId}")
    public R getAlbumSong(@PathVariable Integer albumId){
        return new R(200, AuthConstant.SUCCESS,songService.getSongByAlbum(albumId));
    }

    @GetMapping("/playHistory/{user_id}")
    public R getPlayHistory(@PathVariable Integer user_id) {
        return new R(200, AuthConstant.SUCCESS, songService.getPlayHistory(user_id));
    }

    @PutMapping("/playHistory")
    public R addHistory(@RequestParam("user_id") Integer user_id, @RequestParam("song_id") Integer song_id) {
        songService.updatePlayHistory(user_id, song_id);
        return new R(200, AuthConstant.SUCCESS, null);
    }
}
