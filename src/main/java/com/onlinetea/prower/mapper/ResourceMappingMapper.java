package com.onlinetea.prower.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onlinetea.prower.mapper.dto.ResourceMappingPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceMappingMapper extends BaseMapper<ResourceMappingPO> {
    /**
     * 保存或更新映射关系
     *
     * @param poList 映射关系
     */
    void saveOrUpdateMapping(@Param("poList") List<ResourceMappingPO> poList);

    /**
     * 查询cmdb推送菜单列表的cmdb资源信息
     *
     * @param resource 是否只看推送
     * @return 映射关系列表
     */
    List<ResourceMappingPO> queryCmdbPushPreview(@Param("resource") String resource);
}
