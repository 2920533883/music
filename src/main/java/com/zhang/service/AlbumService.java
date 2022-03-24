package com.zhang.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zhang.bean.Album;
import com.zhang.bean.Singer;
import com.zhang.bean.Song;
import com.zhang.mapper.AlbumMapper;
import com.zhang.mapper.SingerMapper;
import com.zhang.mapper.SongMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(isolation = Isolation.READ_COMMITTED)
@Service
public class AlbumService {
    @Resource
    AlbumMapper albumMapper;
    @Resource
    SingerMapper singerMapper;
    @Resource
    SongMapper songMapper;
    public List<Album> getAlbumBySinger(String singer_id, Integer start, Integer offset) {
        return albumMapper.getAlbumBySingerId("%\"" + singer_id + "\"%", start, offset);
    }

    public List<Album> getAlbumByName(String name, Integer start, Integer offset) {
        return albumMapper.getOnePageAlbumByNameFuzzily("%" + name + "%", start, offset);
    }

    public Map<String,Object> getAlbumById(Integer album_id){
        Map<String,Object> res = new HashMap<>();
        Album album = albumMapper.getAlbumById(album_id);
        JSONArray array = JSON.parseArray(album.getSinger_list());
        List<Song> songList = new ArrayList<>();
        List<Singer> singerList = new ArrayList<>();
        for (Object o : array) {
            Singer singer = singerMapper.getSingerById(Integer.parseInt(o.toString()));
            singerList.add(singer);
        }
        array = JSON.parseArray(album.getSong_list());
        for (Object o : array) {
            Song song = songMapper.getSongById(Integer.parseInt(o.toString()));
            songList.add(song);
        }
        res.put("album", album);
        res.put("singer", singerList);
        res.put("song", songList);
        return res;
    }
}
