package com.zhang.service;

import com.zhang.bean.Love;
import com.zhang.bean.Play;
import com.zhang.bean.Song;
import com.zhang.bean.User;
import com.zhang.mapper.LoveMapper;
import com.zhang.mapper.PlayMapper;
import com.zhang.mapper.SongMapper;
import com.zhang.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;


@Transactional(isolation = Isolation.READ_COMMITTED)
@Service
public class SongService {

    @Resource
    SongMapper songMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    PlayMapper playMapper;

    @Resource
    LoveMapper loveMapper;

    public List<Song> getHotSong() {
        return songMapper.getHotSong();
    }

    public Map<String, Object> getOnePageSongByTag(Integer start, Integer offset, String tag) {
        Map<String, Object> res = new HashMap<>();
        List<Song> songList = songMapper.getOnePageSongByTag(start, offset, "%" + tag + "%");
        int total = songMapper.getSongByTagTotal("%" + tag + "%");
        res.put("song", songList);
        res.put("total", total);
        return res;
    }

    public List<Song> getSongByName(String name) {
        return songMapper.getSongByName("%" + name + "%");
    }

    public List<Song> getRecommend(Integer user_id) throws Exception {
        User user = userMapper.selectUserByID(user_id);
        if (user.getLove_tag().size() == 0) return null;
        else {
            ArrayList<Song> recommendSongList = new ArrayList<>();
            ArrayList<String> ls = new ArrayList<>();
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream("D:\\360MoveData\\Users\\22972\\Desktop\\毕业设计\\我的\\实现代码\\recommend\\predict_res.txt"), StandardCharsets.UTF_8);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                ls.add(lineTxt);
            }
            read.close();
            if (user_id - 1 < ls.size()) {
                String content = ls.get(user_id - 1);
                String[] s = content.split(" ");
                for (int i = 1; i < s.length; i++) {
                    recommendSongList.add(songMapper.getSongById(Integer.valueOf(s[i])));
                }
                return recommendSongList;
            } else {
                return null;
            }
        }

    }

    public boolean loveSong(Integer userId, Integer songId) {
        Love checkLove = loveMapper.checkIfLove(userId, songId);
        if (checkLove != null) return false;
        Love love = new Love();
        love.setSong_id(songId);
        love.setUser_id(userId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        love.setCreate_time(sdf.format(new Date()));
        loveMapper.insertLove(love);
        return true;
    }

    public void unLoveSong(Integer userId, Integer songId) {
        loveMapper.deleteLove(userId, songId);
    }

    public Map<String, Object> getLoveSong(Integer userId, Integer start, Integer offset) {
        Map<String, Object> res = new HashMap<>();
        List<Love> loveList = loveMapper.getLoveByUserId(userId, start, offset);
        int total = loveMapper.getLoveTotal(userId);
        List<Song> songList = new ArrayList<>();
        for (Love love : loveList) {
            Song song = songMapper.getSongById(love.getSong_id());
            songList.add(song);
        }
        res.put("total", total);
        res.put("song", songList);
        return res;
    }

    public void deleteLoveSong(Integer userId, Integer songId) {
        loveMapper.deleteLove(userId, songId);
    }

    public List<Song> getSongByAlbum(Integer album_id) {
        return songMapper.getSongByAlbumId(album_id);
    }

    public Map<String, Object> getPlayHistory(Integer user_id) {
        Map<String, Object> res = new HashMap<>();
        List<Play> playList = playMapper.getPlayHistory(user_id);
        res.put("play", playList);
        List<Song> songList = new ArrayList<>();
        for (Play play : playList) {
            Song song = songMapper.getSongById(play.getSong_id());
            songList.add(song);
        }
        res.put("song", songList);
        return res;
    }


    public void updatePlayHistory(Integer user_id, Integer song_id) {
        if (playMapper.checkPlayHistory(user_id, song_id) != null) {
            playMapper.updatePlayHistory(user_id, song_id);
        } else playMapper.addPlayHistory(user_id, song_id);
    }
}
