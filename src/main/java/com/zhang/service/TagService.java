package com.zhang.service;

import com.zhang.bean.Tag;
import com.zhang.mapper.TagMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(isolation = Isolation.READ_COMMITTED)
@Service
public class TagService {
    @Resource
    TagMapper tagMapper;
    public Map<String, List<Tag>> getAllTag(){
        HashMap<String, List<Tag>> res = new HashMap<>();
        List<Tag> allFatherTag = tagMapper.getTagsByFid(0);
        allFatherTag.forEach(tag -> {
            res.put(tag.getTag_name(), tagMapper.getTagsByFid(tag.getTag_id()));
        });
        return res;
    }
}
