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
     * @param start 页数
     * @param offset 每页数量
     * @return R
     */
    @GetMapping("/search/album/{name}")
    public R getAlbumByName(@PathVariable String name, @RequestParam Integer start, @RequestParam Integer offset){
        return new R(200, AuthConstant.SUCCESS, albumService.getAlbumByName(name, start, offset));
    }

    /**
     * 专辑详情
     * @param albumId 专辑ID
     * @return R
     */
    @GetMapping("/album/detail/{albumId}")
    public R getAlbumById(@PathVariable Integer albumId){
        return new R(200, AuthConstant.SUCCESS, albumService.getAlbumById(albumId));
    }

}
