package com.zhang.task;

import com.csvreader.CsvWriter;
import com.zhang.bean.Love;
import com.zhang.bean.Play;
import com.zhang.bean.Rating;
import com.zhang.mapper.LoveMapper;
import com.zhang.mapper.PlayMapper;
import com.zhang.mapper.SongMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 定时任务
 */
@Component
public class EnduranceScheduled {
    @Resource
    PlayMapper playMapper;

    @Resource
    LoveMapper loveMapper;

    @Resource
    SongMapper songMapper;
    // 每天凌晨1点执行
    @Scheduled(cron = "0 0 23 * * *")
    public void calculatePlayLoveNum(){
        HashMap<Integer, Integer> playMap = new HashMap<>();
        List<Play> allPlay = playMapper.getAllPlay();
        for (Play play : allPlay) {
            Integer song_id = play.getSong_id();
            Integer play_num = play.getPlay_num();
            if (playMap.containsKey(song_id)){
                playMap.put(song_id, playMap.get(song_id) + play_num);
            }
            else {
                playMap.put(song_id, play_num);
            }
        }
        playMap.forEach((song_id, play_num) -> {
            songMapper.updatePlayNum(song_id, play_num);

        });
        HashMap<Integer, Integer> loveMap = new HashMap<>();
        List<Love> allLove = loveMapper.getAllLove();
        for (Love love : allLove) {
            Integer song_id = love.getSong_id();
            if (loveMap.containsKey(song_id)){
                loveMap.put(song_id, loveMap.get(song_id) + 1);
            }
            else {
                loveMap.put(song_id, 1);
            }
        }
        loveMap.forEach((song_id, love_num) -> {
            songMapper.updateLoveNum(song_id, love_num);
        });
    }

    @Scheduled(cron = "0 25 5 * * *")
    public void calculateRating() throws IOException {
        String path = "D:\\360MoveData\\Users\\22972\\Desktop\\毕业设计\\我的\\推荐系统\\song_ratings.csv";
        List<Play> allPlay = playMapper.getAllPlay();
        HashMap<Integer, ArrayList<Rating>> res = new HashMap<>();
        for (Play play : allPlay) {
            Integer userId = play.getUser_id();
            Integer songId = play.getSong_id();
            Integer play_num = play.getPlay_num();
            double rating = 0;
            if (play_num > 0 && play_num < 5) rating = 0.5;
            else if (play_num >= 5 && play_num < 10) rating = 1.0;
            else if (play_num >= 10 && play_num < 20) rating = 1.5;
            else if (play_num >= 20) rating = 2.0;
            if (res.containsKey(userId)) {
                ArrayList<Rating> ratings = res.get(userId);
                ratings.add(new Rating(songId, rating));
            }
            else {
                ArrayList<Rating> ratings = new ArrayList<>();
                ratings.add(new Rating(songId, rating));
                res.put(userId, ratings);
            }
        }
        List<Love> allLove = loveMapper.getAllLove();
        for (Love love : allLove) {
            int userId = love.getUser_id();
            int songId = love.getSong_id();
            if (res.containsKey(userId)) {
                ArrayList<Rating> ratings = res.get(userId);
                int index = ratings.indexOf(new Rating(songId, 0.0));
                if (index == -1){
                    ratings.add(new Rating(songId,2.0));
                }
                else {
                    Rating bean = ratings.get(index);
                    bean.addRating(2.0);
                    ratings.set(index, bean);
                }
            }
            else {
                ArrayList<Rating> ratings = new ArrayList<>();
                ratings.add(new Rating(songId,2.0));
                res.put(userId, ratings);
            }
        }
        Set<Integer> integers = res.keySet();
        CsvWriter csvWriter = new CsvWriter(path, ',', StandardCharsets.UTF_8);
        // 写表头
        String[] csvHeaders = { "userId", "songId", "rating", "tag"};
        csvWriter.writeRecord(csvHeaders);
        for (Integer integer : integers) {
            ArrayList<Rating> ratings = res.get(integer);
            for (Rating rating : ratings) {
                String[] csvContent = {String.valueOf(integer), String.valueOf(rating.song_id), String.valueOf(rating.rating)};
                csvWriter.writeRecord(csvContent);
            }
        }
        csvWriter.close();
    }
}
