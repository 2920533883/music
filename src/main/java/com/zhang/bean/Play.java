package com.zhang.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;

// 播放记录实体
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@TableName(autoResultMap = true)
public class Play implements Serializable {
    Integer id;
    Integer user_id;
    Integer song_id;
    Integer play_num;
}

