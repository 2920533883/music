package com.zhang.service;

import com.zhang.bean.Album;
import com.zhang.bean.Singer;
import com.zhang.mapper.AlbumMapper;
import com.zhang.mapper.SingerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional(isolation = Isolation.READ_COMMITTED)
@Service
public class AlbumService {
    @Resource
    AlbumMapper albumMapper;

    public List<Album> getAlbumBySinger(String singer_id, Integer start, Integer offset) {
        return albumMapper.getAlbumBySingerId("%\"" + singer_id + "\"%", start, offset);
    }

    public List<Album> getAlbumByName(String name, Integer start, Integer offset) {
        return albumMapper.getOnePageAlbumByNameFuzzily("%" + name + "%", start, offset);
    }

    public Album getAlbumById(Integer album_id){
        return albumMapper.getAlbumById(album_id);
    }
}
