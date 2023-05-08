package com.onlinetea.prower.Service.impl;

import com.onlinetea.prower.Bean.storyInfo.ChapterElemt;
import com.onlinetea.prower.Bean.storyInfo.ChapterInfo;
import com.onlinetea.prower.Bean.storyInfo.SearchContent;
import com.onlinetea.prower.Service.SearchStoryService;
import com.onlinetea.prower.mapper.chapterDao.ChapterInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SearchStoryServiceImpl implements SearchStoryService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ChapterInfoMapper chapterInfoMapper;


    final static String iniUrl = "https://www.ibiquge.la";


    public static void main(String[] args) throws IOException {
        ChapterInfo chapter = new ChapterInfo();
        String url = "https://www.ibiquge.la/64/64663/";
        Element allDoc  = null;
        try{
            allDoc  = Jsoup.parse(new URL(url),5000);
            String add = "";
            List<Element> chapters = allDoc.select("dd");
            for(Element element:chapters){
                ChapterElemt chapterElemt = new ChapterElemt();
                String chapterName = element.select("a").toString();
                String chapterUrl = element.select("a").attr("href");
                chapterElemt.setChapterName(chapterName);
                chapterElemt.setChapterUrl(chapterUrl);
            }
            add = "111";
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }

    /**
     * 查询默认站https://www.ibiquge.la/modules/article/waps.php
     * 查询接口
     *
     * @param detail 查询字段
     * @return List<SearchContent> 返回小说列表——小说名、小说主页面、作者
     *
     * */
    @Override
    public List<SearchContent> searchForResult(String detail) {

        //此处加编码格式转换,否则，返回值中，中文会乱码
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "multipart/form-data");
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        MultiValueMap<String, String> reqMap = new LinkedMultiValueMap<>();
        // 以form-data格式，发送post请求
        reqMap.add("searchkey", detail);
        // 向指定网站发送查询请求，post请求，带上要查询的小说名
        String result = restTemplate.postForObject("https://www.ibiquge.la/modules/article/waps.php",reqMap,String.class);
        // 解析返回的查询结果页面
        Element doc = Jsoup.parseBodyFragment(result);
        // 解析标签，获得所有的小说
        List<Element> searchResultList = doc.select("tr");
        List<SearchContent> resultList = new ArrayList<>();
        // 指定网站的第一条数据无效，去除
        searchResultList.remove(0);

        for(Element element:searchResultList){
            SearchContent resultCon = new SearchContent();
            // 获得对应小说的主页链接，提供小程序中点击对应小说时，传递主页的链接
            resultCon.setReadAddress(element.selectFirst("a").attr("href"));
            // 获取小说名
            resultCon.setResultName(element.selectFirst("a").text());
            List<Element> content =  element.getElementsByClass("even");
            if(content.size()>1){
                resultCon.setCreaterName(element.getElementsByClass("even").get(1).text());
            }
            resultList.add(resultCon);
//             try {
//                // 解析具体小说地址，添加小说图片到返回信息中
//                Element docInfo = Jsoup.parse(new URL(resultCon.getReadAddress()),2000);
//                List<Element> listStr = docInfo.select("img");
//                for(Element element1 : listStr){
//                   if(element1.attr("alt").equals(resultCon.getResultName())){
//                       resultCon.setImageAddress(element1.attr("src"));
//                   }
//                }
//            } catch (IOException e) {
//                log.error("解析出错 ===> "+e.getMessage());
//            }
        }

        return resultList;
    }

    /**
     *
     * url 类似于这样的地址 https://www.ibiquge.la/64/64663/
     * 解析小说的主页，获取第一章的链接，用Jsoup解析第一章的正文内容，并返回
     *
     * @param url 具体小说的主页url——该页面主要是所有的章节数据举例
     *
     * */
    @Override
    public ChapterInfo getDetial(String url) {
        // 正文页面解析的元素对象
        Element allDoc  = null;
        ChapterInfo chapter = new ChapterInfo();
        try{
            allDoc  = Jsoup.parse(new URL(url),5000);

            // 只获得第一章的url，并解析第一章网页
            String[] firstC = allDoc.selectFirst("dd").select("a").attr("href").split("/");
            String chapterHtml = firstC[firstC.length-1];
            // 开始解析第一章内容
            chapter = getChapterContent(url+chapterHtml,chapter);
            String name = allDoc.select("h1").text();
            String menuUrl = chapterInfoMapper.queryMainPageUrl(name);
            if(menuUrl==null||menuUrl.equals("")){
                chapterInfoMapper.saveChapterListUrl(name,url);
            }
            // 解析主页的所有章节信息——章节名和对应的章节url
            List<ChapterElemt> info = explainMenu(allDoc);
            // 解析出章节内容和链接

            // 设置章节信息返回
            chapter.setChapters(info);
        }catch (Exception e){
            log.info(e.getMessage());
        }
        return chapter;
    }

    @Override
    public ChapterInfo getChapterCon(String url) {
        ChapterInfo chapter = new ChapterInfo();
        try {
            chapter=getChapterContent(url,chapter);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return chapter;
    }

    /**
     * 解析指定章节的正文内容
     *
     * */
    @Override
    public ChapterInfo toTargetChapter(String url) {
        ChapterInfo chapter = new ChapterInfo();
        try {
            chapter=getChapterContent(iniUrl+url,chapter);
        } catch (IOException e) {
            log.info(e.getMessage());
        }

        return chapter;
    }

    /**
     * 解析具体的阅读页面
     *
     * */
    public ChapterInfo getChapterContent(String url,ChapterInfo chapter) throws IOException {
        String initUrl = "https://www.ibiquge.la";
        String result  = "";
        Element  firstDoc = Jsoup.parse(new URL(url),5000);
        // 第一章，标题
        String titleName = firstDoc.select("h1").text();
        String name = firstDoc.getElementsByClass("con_top").select("a").get(2).text();
        String menuUrl = chapterInfoMapper.queryMainPageUrl(name);
        Element  menuDoc = Jsoup.parse(new URL(menuUrl),5000);
        List<ChapterElemt> info = explainMenu(menuDoc);
        chapter.setChapters(info);

        // 文章正文
        Element chapterElement =  firstDoc.getElementById("content");
        if(chapterElement!=null){
            //去除底部广告
            chapterElement.select("a").remove();
            chapterElement.select("p").remove();
            Elements hrefs =  firstDoc.select("a");
            List<Node> textArr =  chapterElement.childNodes();
            // 章节内容 strText
            StringBuilder strText = new StringBuilder();
            strText.append("\r\r"+titleName+"\r\n\r\n");
            for(int i = 0;i<textArr.size();i++){
                strText.append(textArr.get(i));
            }
            String[] str0 = url.split("/");
            StringBuilder zoneUrl = new StringBuilder();
            for(int i=0;i<str0.length-1;i++){
                zoneUrl.append(str0[i]);
            }

            String nextUrl = "";
            for(Element element:hrefs){
                if(StringUtils.equals("下一章",element.text())){
                    // 下一章内容,/34/34606/16073199.html
                    String str1 = element.attr("href");
                    nextUrl = initUrl+str1;
                    chapter.setNextUrl(initUrl+str1);
                    log.info("下一章链接 "+initUrl+str1);
                    break;
                }
            }
            result = strText.toString().replaceAll("&nbsp;&nbsp;&nbsp;&nbsp;","    ")
                    .replaceAll("<br> <br>","\r\n").replaceAll("<br>","");

            chapter.setContent(result);

        }else{
            log.info("该网站页面参数有变动 ");
        }
        return chapter;
    }

    /**
     * 用于解析小说主页的章节信息
     *
     * */
    public List<ChapterElemt> explainMenu(Element allDoc){
        List<Element> urls = allDoc.select("dd");
        List<ChapterElemt> info = new ArrayList<>();
        for(Element element:urls){
            ChapterElemt elemt = new ChapterElemt();
            elemt.setChapterUrl(element.select("a").attr("href"));
            elemt.setChapterName(element.select("a").text());
            info.add(elemt);
        }
        return info;
    }


}
