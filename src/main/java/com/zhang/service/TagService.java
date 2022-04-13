package com.zhang.service;

import com.zhang.bean.Tag;
import com.zhang.mapper.TagMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Transactional(isolation = Isolation.READ_COMMITTED)
@Service
public class TagService {
    @Resource
    TagMapper tagMapper;

    public ArrayList<HashMap<String, Object>> getAllTag() {
        ArrayList<HashMap<String, Object>> res = new ArrayList<>();
        List<Tag> fTagList = tagMapper.getTagsByFid(0);
        fTagList.forEach(fTag -> {
            List<Tag> cTagList = tagMapper.getTagsByFid(fTag.getTag_id());
            ArrayList<HashMap<String, Object>> children = new ArrayList<>();
            cTagList.forEach(cTag -> {
                HashMap<String, Object> cMap = new HashMap<>();
                cMap.put("value", cTag.getTag_name());
                cMap.put("label", cTag.getTag_name());
                children.add(cMap);
            });
            HashMap<String, Object> fMap = new HashMap<>();
            fMap.put("value", fTag.getTag_name());
            fMap.put("label", fTag.getTag_name());
            fMap.put("children", children);
            res.add(fMap);
        });
        return res;
    }

    public Set<Tag> getChildrenTag() {
        List<Tag> fTagList = tagMapper.getTagsByFid(0);
        Set<Tag> res = new HashSet<>();
        fTagList.forEach(fTag -> {
            List<Tag> cTagList = tagMapper.getTagsByFid(fTag.getTag_id());
            res.addAll(cTagList);
        });
        return res;
    }
}
