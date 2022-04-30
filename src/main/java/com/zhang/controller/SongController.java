package com.zhang.controller;

import com.zhang.bean.*;
import com.zhang.mapper.LoveMapper;
import com.zhang.mapper.PlayMapper;
import com.zhang.mapper.SongMapper;
import com.zhang.service.SongService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 歌曲控制类
 */
@RestController
public class SongController {
    @Resource
    SongService songService;


    /**
     * 获取热门歌曲
     *
     * @return R
     */
    @GetMapping("/hotSong")
    public R getHotSong(@RequestParam(value = "userId", required = false) Integer userId) {
        return new R(200, AuthConstant.SUCCESS, songService.getHotSong(userId));
    }

    /**
     * 根据标签获取一页歌曲
     *
     * @param tag    标签
     * @param start  页数
     * @param offset 每页数量
     * @return R
     */
    @GetMapping("/songByTag/{tag}")
    public R getOnePageSongByTag(@PathVariable String tag, @RequestParam Integer start, @RequestParam Integer offset,@RequestParam(required = false) Integer userId) {
        return new R(200, AuthConstant.SUCCESS, songService.getOnePageSongByTag(start, offset, tag, userId));
    }

    /**
     * 模糊搜索
     *
     * @param name 歌名
     * @return R
     */
    @GetMapping("/search/song/{name}")
    public R getOnePageSongByName(@PathVariable String name, @RequestParam(required = false) Integer userId) {
        List<Song> SongList = songService.getSongByName(name, userId);
        return new R(200, AuthConstant.SUCCESS, SongList);
    }

    /**
     * 收藏歌曲
     *
     * @param user_id 用户ID
     * @param song_id 歌曲ID
     * @return R
     */
    @PutMapping("/loveSong")
    public R loveSong(@RequestParam("user_id") Integer user_id, @RequestParam("song_id") Integer song_id) {
        if (songService.loveSong(user_id, song_id)) return new R(200, AuthConstant.SUCCESS, null);
        else return new R(400, AuthConstant.ERROR, null);
    }

    /**
     * 获取用户收藏歌曲
     *
     * @param userId 用户ID
     * @return R
     */
    @GetMapping("/loveSong/{userId}")
    public R getLoveSong(@PathVariable Integer userId, @RequestParam Integer start, @RequestParam Integer offset) {
        return new R(200, AuthConstant.SUCCESS, songService.getLoveSong(userId, start, offset));
    }

    /**
     * 取消收藏歌曲
     *
     * @param user_id 用户ID
     * @param song_id 歌曲ID
     * @return R
     */
    @DeleteMapping("/unLoveSong")
    public R unLoveSong(@RequestParam("user_id") Integer user_id, @RequestParam("song_id") Integer song_id) {
        songService.deleteLoveSong(user_id, song_id);
        return new R(200, AuthConstant.SUCCESS, null);
    }

    /**
     * 获取专辑里的歌曲
     *
     * @param albumId 专辑ID
     * @return R
     */
    @GetMapping("/song/album/{albumId}")
    public R getAlbumSong(@PathVariable Integer albumId, @RequestParam(value = "userId", required = false) Integer userId) {
        return new R(200, AuthConstant.SUCCESS, songService.getSongByAlbum(albumId, userId));
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

    @GetMapping("/recommend/{user_id}")
    public R getRecommend(@PathVariable Integer user_id) throws Exception {
        return new R(200, AuthConstant.SUCCESS, songService.getRecommend(user_id));
    }

    @Resource
    PlayMapper playMapper;
    @Resource
    SongMapper songMapper;
    @Resource
    LoveMapper loveMapper;
    @GetMapping("/a")
    public void calculatePlayLoveNum(){
        HashMap<Integer, Integer> playMap = new HashMap<>();
        List<Play> allPlay = playMapper.getAllPlay();
        for (Play play : allPlay) {
            Integer song_id = play.getSong_id();
            Integer play_num = play.getPlay_num();
            if (playMap.containsKey(song_id)){
                playMap.put(song_id, playMap.get(song_id) + play_num);
            }
            else {
                playMap.put(song_id, play_num);
            }
        }
        playMap.forEach((song_id, play_num) -> {
            songMapper.updatePlayNum(song_id, play_num);

        });
        HashMap<Integer, Integer> loveMap = new HashMap<>();
        List<Love> allLove = loveMapper.getAllLove();
        for (Love love : allLove) {
            Integer song_id = love.getSong_id();
            if (loveMap.containsKey(song_id)){
                loveMap.put(song_id, loveMap.get(song_id) + 1);
            }
            else {
                loveMap.put(song_id, 1);
            }
        }
        loveMap.forEach((song_id, love_num) -> {
            songMapper.updateLoveNum(song_id, love_num);
        });
    }
}
