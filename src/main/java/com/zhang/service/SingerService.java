package com.zhang.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zhang.bean.Album;
import com.zhang.bean.Singer;
import com.zhang.mapper.AlbumMapper;
import com.zhang.mapper.SingerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Transactional(isolation = Isolation.READ_COMMITTED)
@Service
public class SingerService {
    @Resource
    SingerMapper singerMapper;

    @Resource
    AlbumMapper albumMapper;

    public List<Singer> getSinger(Integer start, Integer offset){
        return singerMapper.getOnePageSinger(start, offset);
    }

    public List<Singer> getSingerByPinYin(String f_py){
        return singerMapper.getOnePageSingerByPinYin(f_py);
    }

    public List<Singer> getSingerByNameFuzzily(String singer_name, Integer start, Integer offset){
        return singerMapper.getOnePageSingerByNameFuzzily("$"+singer_name+"$", start, offset);
    }

    public HashMap<String, Object> getSingerDetail(Integer singer_id){
        HashMap<String, Object> res = new HashMap<String, Object>();
        Singer singer = singerMapper.getSingerById(singer_id);
        JSONArray array = JSON.parseArray(singer.getAlbum_list());
        ArrayList<Album> albumList = new ArrayList<>();
        for (Object o : array) {
            Album album = albumMapper.getAlbumById(Integer.parseInt(o.toString()));
            if (album != null) albumList.add(album);
        }
        res.put("singer", singer);
        res.put("album", albumList);
        return res;
    }
}
