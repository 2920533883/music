package com.zhang.controller;

import com.zhang.bean.AuthConstant;
import com.zhang.bean.R;
import com.zhang.service.SingerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 歌手控制类
 */
@RestController
public class SingerController {
    @Resource
    SingerService singerService;

    /**
     * 获取所有歌手
     * @param start 页数
     * @param offset 每页数量
     * @return R
     */
    @GetMapping("/singer/all")
    public R getAllSinger(@RequestParam Integer start, @RequestParam Integer offset){
        return new R(200, AuthConstant.SUCCESS,singerService.getSinger(start, offset));
    }

    /**
     * 按首字母查询歌手
     * @param f_py 拼音首字母
     * @param start 页数
     * @param offset 每页数量
     * @return R
     */
    @GetMapping("/singer/py/{f_py}")
    public R getSingerByFPY(@PathVariable String f_py, @RequestParam Integer start, @RequestParam Integer offset){
        return new R(200, AuthConstant.SUCCESS, singerService.getSingerByPinYin(f_py, start, offset));
    }

    /**
     * 模糊搜索歌手
     * @param name 歌手名
     * @param start 页数
     * @param offset 每页数量
     * @return R
     */
    @GetMapping("/singer/search/{name}")
    public R getSingerByName(@PathVariable String name, @RequestParam Integer start, @RequestParam Integer offset){
        return  new R(200, AuthConstant.SUCCESS, singerService.getSingerByNameFuzzily(name, start, offset));
    }

    /**
     * 获取歌手细节
     * @param singer_id 歌手ID
     * @return R
     */
    @GetMapping("/singer/detail/{singer_id}")
    public R getSingerDetail(@PathVariable Integer singer_id){
        return new R(200, AuthConstant.SUCCESS, singerService.getSingerDetail(singer_id));
    }
}
