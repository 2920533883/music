package com.zhang.service;

import com.zhang.bean.Love;
import com.zhang.bean.Play;
import com.zhang.bean.Song;
import com.zhang.bean.User;
import com.zhang.mapper.LoveMapper;
import com.zhang.mapper.PlayMapper;
import com.zhang.mapper.SongMapper;
import com.zhang.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.ibatis.annotations.Mapper;
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

    public List<Song> getHotSong(Integer userId) {
        List<Song> hotSong = songMapper.getHotSong();
        for (Song song : hotSong) {
            if (loveMapper.checkIfLove(userId, song.getSong_id()) != null) song.setIsLove(true);
        }
        return hotSong;
    }

    public Map<String, Object> getOnePageSongByTag(Integer start, Integer offset, String tag, Integer userId) {
        Map<String, Object> res = new HashMap<>();
        List<Song> songList = songMapper.getOnePageSongByTag(start, offset, "%" + tag + "%");
        for (Song song : songList) {
            if (loveMapper.checkIfLove(userId, song.getSong_id()) != null) song.setIsLove(true);
        }
        int total = songMapper.getSongByTagTotal("%" + tag + "%");
        res.put("song", songList);
        res.put("total", total);
        return res;
    }

    public List<Song> getSongByName(String name, Integer userId) {
        List<Song> songList = songMapper.getSongByName("%" + name + "%");
        for (Song song : songList) {
            if (loveMapper.checkIfLove(userId, song.getSong_id()) != null) song.setIsLove(true);
        }
        return songList;
    }


    public List<Song> getRecommend(Integer user_id) throws Exception {
        User user = userMapper.selectUserByID(user_id);
        HashSet<String> love_tag = new HashSet<>(user.getLove_tag());
        if (love_tag.contains("")) return null;
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
                    Song song = songMapper.getSongById(Integer.valueOf(s[i]));
                    if (loveMapper.checkIfLove(user_id, song.getSong_id()) != null) song.setIsLove(true);
                    recommendSongList.add(song);
                }
                return recommendSongList;
            } else {
                String profession = user.getProfession();
                if (profession != null){
                    if (profession.equals("学生")) love_tag.addAll(Arrays.asList("安静", "流行", "清晨"));
                    if (profession.equals("上班族")) love_tag.addAll(Arrays.asList("流行",  "下午茶"));
                    if (profession.equals("运动员")) love_tag.addAll(Arrays.asList("运动", "流行", "兴奋"));
                    if (profession.equals("艺术家")) love_tag.addAll(Arrays.asList("New Age", "世界音乐", "古典"));
                    if (profession.equals("商人")) love_tag.addAll(Arrays.asList("流行", "夜晚", "下午茶"));
                }
                ArrayList<Rating> ratingArrayList = new ArrayList<>();
                List<Song> randomSong = songMapper.getRandomSong();
                for (Song song : randomSong) {
                    HashSet<String> user_tag = new HashSet<>(love_tag);
                    HashSet<String> song_tag = new HashSet<>(song.getSong_tag());
                    HashSet<String> total = new HashSet<>();
                    total.addAll(love_tag);
                    total.addAll(song_tag);
                    user_tag.retainAll(song_tag);
                    ratingArrayList.add(new Rating(song, (double) user_tag.size() / (double) total.size()));
                }
                ratingArrayList.sort((o1, o2) -> Double.compare(o2.rating, o1.rating));
                ArrayList<Song> res = new ArrayList<>();
                for (int i = 0; i < 15; ++i) {
                    Song song = ratingArrayList.get(i).song;
                    if (loveMapper.checkIfLove(user_id, song.getSong_id()) != null) song.setIsLove(true);
                    res.add(song);
                }
                return res;
            }
        }
    }

    @Getter
    @Mapper
    @AllArgsConstructor
    static
    class Rating {
        Song song;
        double rating;

        @Override
        public String toString() {
            return "song_id=" + song.getSong_id() +
                    ", rating=" + rating +
                    '\n';
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

    public Map<String, Object> getLoveSong(Integer userId, Integer start, Integer offset) {
        Map<String, Object> res = new HashMap<>();
        List<Love> loveList = loveMapper.getLoveByUserId(userId, start, offset);
        int total = loveMapper.getLoveTotal(userId);
        List<Song> songList = new ArrayList<>();
        for (Love love : loveList) {
            Song song = songMapper.getSongById(love.getSong_id());
            if (loveMapper.checkIfLove(userId, song.getSong_id()) != null) song.setIsLove(true);
            songList.add(song);
        }
        res.put("total", total);
        res.put("song", songList);
        return res;
    }

    public void deleteLoveSong(Integer userId, Integer songId) {
        loveMapper.deleteLove(userId, songId);
    }

    public List<Song> getSongByAlbum(Integer album_id, Integer user_id) {
        if (user_id == null) return songMapper.getSongByAlbumId(album_id);
        List<Song> songList = songMapper.getSongByAlbumId(album_id);
        for (Song song : songList) {
            if (loveMapper.checkIfLove(user_id, song.getSong_id()) != null) song.setIsLove(true);
        }
        return songList;
    }

    public Map<String, Object> getPlayHistory(Integer user_id) {
        Map<String, Object> res = new HashMap<>();
        List<Play> playList = playMapper.getPlayHistory(user_id);
        res.put("play", playList);
        List<Song> songList = new ArrayList<>();
        for (Play play : playList) {
            Song song = songMapper.getSongById(play.getSong_id());
            if (loveMapper.checkIfLove(user_id, song.getSong_id()) != null) song.setIsLove(true);
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
