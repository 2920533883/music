package com.zhang.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zhang.bean.Album;
import com.zhang.bean.Song;
import com.zhang.mapper.AlbumMapper;
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

    @Resource
    AlbumMapper albumMapper;

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

    public List<Song> getLoveSong(Integer userId){
        String loveSong = userMapper.getLoveSongByUserId(userId);
        List<Song> songList = new ArrayList<>();
        JSONArray array = JSON.parseArray(loveSong);
        for (Object o : array) {
            Song song = songMapper.getSongById(Integer.valueOf(o.toString()));

            songList.add(song);
        }
        return songList;
    }

    public void deleteLoveSong(Integer userId, String songId){
        String loveSong = userMapper.getLoveSongByUserId(userId);
        JSONArray array = JSON.parseArray(loveSong);
        array.remove(songId);
        userMapper.updateLoveSongByUserId(userId, array.toString());
    }

    public List<Song> getSongByAlbum(Integer album_id){
        Album album = albumMapper.getAlbumById(album_id);
        JSONArray array = JSON.parseArray(album.getSong_list());
        ArrayList<Song> songList = new ArrayList<>();
        for (Object o : array) {
            Song song = songMapper.getSongById(Integer.valueOf(o.toString()));
            songList.add(song);
        }
        return songList;
    }
}
