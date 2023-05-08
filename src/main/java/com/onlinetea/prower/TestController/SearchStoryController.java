package com.onlinetea.prower.TestController;

import com.onlinetea.prower.Bean.storyInfo.ChapterInfo;
import com.onlinetea.prower.Bean.storyInfo.SearchContent;
import com.onlinetea.prower.Service.SearchStoryService;
import com.onlinetea.prower.TestController.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/story")
public class SearchStoryController {

    @Autowired
    SearchService searchService;

    @Autowired
    SearchStoryService search;


    @GetMapping("testConnect")
    public String testWeiXin(){
        return "success";
    }


    /**
     * 搜索小说
     *
     * */
    @GetMapping("searchForResult")
    public List<SearchContent> searchForResult(@RequestParam String detail){
        return search.searchForResult(detail);
    }

    /**
     * 解析出第一章内容
     *
     * */
    @GetMapping("getDetial")
    public ChapterInfo getDetial(@RequestParam String url){
        return search.getDetial(url);
    }

    /**
     * 查看具体小说的正文内容
     *
     * */
    @GetMapping("getChapterCon")
    public ChapterInfo getChapterCon(@RequestParam String url){
        return search.getChapterCon(url);
    }

    /**
     * 实现章节跳转的阅读
     *
     *
     * */

    /**
     * 查看具体小说的正文内容
     *
     * */
    @GetMapping("toTargetChapter")
    public ChapterInfo toTargetChapter(@RequestParam String url){
        return search.toTargetChapter(url);
    }


    /**
     * 需要输入小说的第一页的参数
     *
     * @param search 参数
     *
     * */
    @PostMapping("/analysis")
    public void analysiStory(@RequestBody SearchBean search) {
        searchService.analysiStory(search.getUrl(),search.getInHtml(),search.getRequestID(),search.getStoryName());
    }

    /**
     * 查看具体小说的正文内容
     *
     * */
    @GetMapping("loadAllContent")
    public void loadAllContent(@RequestParam String url) throws IOException, InterruptedException {
         searchService.loadAllContent(url);
    }


}
