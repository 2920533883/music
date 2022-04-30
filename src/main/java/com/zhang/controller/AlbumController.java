package com.zhang.controller;

import com.zhang.bean.AuthConstant;
import com.zhang.bean.R;
import com.zhang.service.AlbumService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 专辑控制类
 */
@RestController
public class AlbumController {
    @Resource
    AlbumService albumService;

    /**
     * 模糊搜索专辑
     * @param name 专辑名
     * @return R
     */
    @GetMapping("/search/album/{name}")
    public R getAlbumByName(@PathVariable String name){
        return new R(200, AuthConstant.SUCCESS, albumService.getAlbumByName(name));
    }

    /**
     * 专辑详情
     * @param albumId 专辑ID
     * @return R
     */
    @GetMapping("/album/detail/{albumId}")
    public R getAlbumById(@PathVariable Integer albumId, @RequestParam(value = "userId", required = false) Integer userId){
        return new R(200, AuthConstant.SUCCESS, albumService.getAlbumById(albumId, userId));
    }

}
