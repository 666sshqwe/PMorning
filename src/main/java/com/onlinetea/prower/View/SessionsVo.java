package com.onlinetea.prower.View;


import lombok.Data;
import java.time.LocalDateTime;


@Data
public class SessionsVo {

    /**主键*/
    long cId;

    /**剧本名*/
    String dramaName;

    /**剧本ID*/
    Long dramaId;

    /**创建者的ID*/
    long createId;

    /**参与者的id*/
    String userIds;

    /**该剧本的参与人数*/
    int userNum;

    /**改剧本应该有的人数*/
    int roleNum;

    /**图片路径*/
    String dramaImage;

    /**场次描述*/
    String sessionDesc;

    /**创建时间*/
    LocalDateTime createTime;

    /**创建时间*/
    LocalDateTime updateTime;

    /**是否反串*/
    String crossGender;
}
