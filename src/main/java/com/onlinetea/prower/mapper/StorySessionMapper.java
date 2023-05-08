package com.onlinetea.prower.mapper;

import com.onlinetea.prower.Bean.StorySessionDetail;
import com.onlinetea.prower.View.SessionsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface StorySessionMapper {

    /**创建剧本场次
     * 因为使用了selectKey返回新增的ID，不能使用注解@Param
     * */
    long createSessions(SessionsVo vo);

    /**查询剧本场次信息*/
    SessionsVo querySession(@Param("sessionId") String sessionId);

    /**更新剧本场次信息*/
    void updateSession(SessionsVo data);

    /**查询场次详细信息，根据场次的ID，查询该场次中，有哪些角色*/
    List<StorySessionDetail> querySessionDetail(@Param("sessionId") String sessionId,
    @Param("sessionType") String sessionType);


    void updateSessionDeatil(StorySessionDetail data);

    void insertSessionDeatil(StorySessionDetail data);

    /**查询所有的场次信息*/
    List<SessionsVo> queryAllSessions(@Param("type")String type);

}
