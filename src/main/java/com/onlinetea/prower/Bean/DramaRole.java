package com.onlinetea.prower.Bean;

import lombok.Data;

@Data
public class DramaRole {

    /**主键-角色ID*/
    long id;

    /**剧本名-标题*/
    String title;

    /**剧本名*/
    String dramaName;

    /**角色名*/
    String roleName;

    /**角色性别*/
    String roleSex;

    /**剧本id*/
    long dramaId;

    /**角色图片*/
    String roleImage;

    /**角色描述*/
    String roleDesc;


}
