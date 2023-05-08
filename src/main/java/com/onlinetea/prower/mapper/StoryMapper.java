package com.onlinetea.prower.mapper;

import com.onlinetea.prower.Bean.DramaInfo;
import com.onlinetea.prower.Bean.DramaRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StoryMapper {

    /**根据剧本类型，获得剧本信息*/
    List<DramaInfo> getDramaInfos(@Param("type") String type);

    /**根据剧本ID，获得剧本对应的角色信息*/
    List<DramaRole> getDramaRole(@Param("dramaId") String dramaId);

}
