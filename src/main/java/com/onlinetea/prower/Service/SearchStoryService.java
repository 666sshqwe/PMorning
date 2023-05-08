package com.onlinetea.prower.Service;

import com.onlinetea.prower.Bean.storyInfo.ChapterInfo;
import com.onlinetea.prower.Bean.storyInfo.SearchContent;

import java.util.List;

public interface SearchStoryService {

   List<SearchContent> searchForResult(String detail);

   ChapterInfo getDetial(String url);

   ChapterInfo getChapterCon(String url);

   ChapterInfo toTargetChapter(String url);

}
