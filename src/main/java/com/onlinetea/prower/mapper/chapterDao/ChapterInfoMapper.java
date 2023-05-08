package com.onlinetea.prower.mapper.chapterDao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ChapterInfoMapper {

    void saveChapterListUrl(@Param("readerName") String readerName, @Param("url") String url);

    String queryMainPageUrl(@Param("targetName") String targetName);

}
