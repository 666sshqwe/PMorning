package com.onlinetea.prower.Bean.storyInfo;

import lombok.Data;

import java.util.List;

@Data
public class ChapterInfo {

    String content;

    String nextUrl;

    List<ChapterElemt> chapters;

}
