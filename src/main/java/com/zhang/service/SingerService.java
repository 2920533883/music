package com.zhang.service;

import com.zhang.bean.Singer;
import com.zhang.bean.Song;
import com.zhang.mapper.SingerMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional(isolation = Isolation.READ_COMMITTED)
@Service
public class SingerService {
    @Resource
    SingerMapper singerMapper;

    public List<Singer> getSinger(Integer start, Integer offset){
        return singerMapper.getOnePageSinger(start, offset);
    }

    public List<Singer> getSingerByPinYin(String f_py, Integer start, Integer offset){
        return singerMapper.getOnePageSingerByPinYin(f_py, start, offset);
    }

    public List<Singer> getSingerByNameFuzzily(String singer_name, Integer start, Integer offset){
        return singerMapper.getOnePageSingerByNameFuzzily("*"+singer_name+"*", start, offset);
    }

    public Singer getSingerById(Integer singer_id){
        return singerMapper.getSingerById(singer_id);
    }


}
