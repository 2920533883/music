package com.zhang.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 消息返回实体
@AllArgsConstructor
@NoArgsConstructor
@Data
public class R {
    Integer code;
    String message;
    Object data;
}
