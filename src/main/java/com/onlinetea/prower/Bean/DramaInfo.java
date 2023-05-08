package com.onlinetea.prower.Bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName
public class DramaInfo {
    /**主键*/
    long id;

    /**剧本名*/
    String dramaName;

    /**描述*/
    String dramaDesc;

    /**图片路径*/
    String dramaImage;

    /**类型*/
    String dramaType;

    /**创建时间*/
    LocalDateTime createTime;
}
