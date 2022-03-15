package com.zhang.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

// 标签实体
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tag implements Serializable {
    Integer tag_id;
    String tag_name;
    String hot_num; // 20000以上为热门
    Integer father_tag;
}
