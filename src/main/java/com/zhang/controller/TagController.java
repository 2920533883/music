package com.zhang.controller;

import com.zhang.bean.AuthConstant;
import com.zhang.bean.R;
import com.zhang.bean.Tag;
import com.zhang.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 标签控制类
 */

@RestController
public class TagController {

    @Resource
    TagService tagService;

    /**
     * 获取所有分类标签
     * @return R
     */
    @GetMapping("/tag")
    public R getAllTags(){
        return new R(200, AuthConstant.SUCCESS, tagService.getAllTag());
    }
}
