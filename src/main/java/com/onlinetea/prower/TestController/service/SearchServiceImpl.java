package com.onlinetea.prower.TestController.service;

import com.onlinetea.prower.Bean.storyInfo.ChapterElemt;
import com.onlinetea.prower.Bean.storyInfo.ChapterInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    /**
     * @param url 网站总地址
     * @param Menu 具体小说的页面参数
     * @param requestID 页面展示小说文字的页面元素名称 <div id = "requestID"><div/>
     *
     * */
    @SneakyThrows
    @Override
    public void analysiStory(String url, String Menu,String requestID,String storyName) {
       String nextUrl = Menu;
       int i = 0;
       do{
           nextUrl = readText(url,nextUrl,requestID,storyName);
           Thread.sleep(5000);
       }while(!StringUtils.equals("text",nextUrl));

    }

    @SneakyThrows
    public String readText(String url, String Menu, String requestID,String storyName){
        Element doc  = null;
        try{
             doc  = Jsoup.parse(new URL(url+Menu),5000);
        }catch (Exception e){
            log.info(e.getMessage());
           readText(url,Menu,requestID,storyName);
        }

        // 获得章节标题
        String titleName = doc.select("h1").text()
                .replaceAll("。","")
                .replaceFirst("章","章----");
        Element content =   doc.getElementById(requestID);
        String nextUrl = "text";
        if(content!=null){
            //去除底部广告
            content.select("a").remove();
            content.select("p").remove();
            Elements hrefs =  doc.select("a");
            List<Node> textArr =  content.childNodes();
            StringBuilder strText = new StringBuilder();
            strText.append("\r\r"+titleName+"\r\n\r\n");
            for(int i = 0;i<textArr.size();i++){
                strText.append(textArr.get(i));
            }
            String text = content.text();
            for(Element element:hrefs){
                if(StringUtils.equals("下一章",element.text())){
                    // 下一章内容
                    nextUrl = element.attr("href");
//                    String[] strMenu = Menu.split("/");
//                    String abled = strMenu[strMenu.length-1];
//                    nextUrl = Menu.replaceAll(abled,nextUrl);
                    break;
                }
            }
            String result = strText.toString().replaceAll("&nbsp;&nbsp;&nbsp;&nbsp;","    ")
                    .replaceAll("<br> <br>","\r\n");
      fileWriter("D:\\MyText\\福宝三岁半\\" + storyName + "-" + titleName + ".txt", result);
            log.info("下一章链接 "+nextUrl);
        }else{
            log.info("该网站页面参数有变动 ");
        }
        return nextUrl;
    }

    /**
     * 输出文件内容
     * @param textPath 文件路径
     * @param text 文字内容
     *
     * */
    public void fileWriter(String textPath,String text){
        try {
          FileWriter writer = new FileWriter(textPath, true);

            writer.write(text);
            writer.close();
        } catch (IOException e) {
            log.info(e.getMessage());
        }

    }

     StringBuilder txtAllPadd = new StringBuilder();


  public static void main(String[] args) throws IOException {
      String initUrl = "https://www.ibiquge.la";
      String result  = "";
      Element  Doc = null;
      try {
          Doc = Jsoup.parse(new URL("https://www.ibiquge.la/64/64663/35355278.html"),5000);
      } catch (IOException e) {
          log.error("解析小说网址出问题了"+e.getMessage());
      }
      // 小说名
      String storyName = Doc.getElementsByClass("con_top").select("a").get(2).text();

      // 章节标题
      String title = Doc.select("h1").text();
      // 文章正文
      Element chapterElement =  Doc.getElementById("content");
      if(chapterElement!=null){
          //去除底部广告
          chapterElement.select("a").remove();
          chapterElement.select("p").remove();
          Elements hrefs =  Doc.select("a");
          List<Node> textArr =  chapterElement.childNodes();
          // 章节内容 strText
          StringBuilder strText = new StringBuilder();
          strText.append("\r\r"+title+"\r\n\r\n");
          for(int i = 0;i<textArr.size();i++){
              strText.append(textArr.get(i));
          }

          String nextUrl = "";
          for(Element element:hrefs){
              if(StringUtils.equals("下一章",element.text())){
                  // 下一章内容,/34/34606/16073199.html
                  String str1 = element.attr("href");
                  nextUrl = initUrl+str1;
                  log.info("下一章链接 "+initUrl+str1);
                  break;
              }
          }
          result = strText.toString().replaceAll("&nbsp;&nbsp;&nbsp;&nbsp;","    ")
                  .replaceAll("<br> <br>","\r\n").replaceAll("<br>","");
//          txtAllPadd.append(result);
//          txtAllPadd.append("===================");

          if(!nextUrl.contains(".html")){
             System.out.println("结束！！！！");
          }

      //          if(!nextUrl.equals("")){
      //
      //              Thread.sleep(1000);
      //              loadAllContent(nextUrl);
      //          }else{
      //              fileWriter("D:\\MyText\\"+storyName+"\\"+storyName + ".txt",
      // txtAllPadd.toString());
      //          }
      System.out.println("hahahahahahahaha");
      }else{
          log.info("该网站页面参数有变动 ");
      }

  }



    /**
     * 整理出一本小说的所有内容
     *
     * */
    public  void loadAllContent(String url) throws InterruptedException {
        String initUrl = "https://www.ibiquge.la";
        String result  = "";
        Element  Doc = null;
        try {
            Doc = Jsoup.parse(new URL(url),5000);
        } catch (IOException e) {
            Thread.sleep(2000);
            log.error("解析小说网址出问题了"+e.getMessage());
            loadAllContent(url);
        }

        // 小说名
        String storyName = Doc.getElementsByClass("con_top").select("a").get(2).text();
        // 章节标题
        String title = Doc.select("h1").text();
        // 文章正文
        Element chapterElement =  Doc.getElementById("content");
        if(chapterElement!=null){
            //去除底部广告
            chapterElement.select("a").remove();
            chapterElement.select("p").remove();
            Elements hrefs =  Doc.select("a");
            List<Node> textArr =  chapterElement.childNodes();
            // 章节内容 strText
            StringBuilder strText = new StringBuilder();
            strText.append("\r\r"+title+"\r\n\r\n");
            for(int i = 0;i<textArr.size();i++){
                strText.append(textArr.get(i));
            }

            String nextUrl = "";
            for(Element element:hrefs){
                if(StringUtils.equals("下一章",element.text())){
                    // 下一章内容,/34/34606/16073199.html
                    String str1 = element.attr("href");
                    nextUrl = initUrl+str1;
                    log.info("下一章链接 "+initUrl+str1);
                    break;
                }
            }
            result = strText.toString().replaceAll("&nbsp;&nbsp;&nbsp;&nbsp;","    ")
                    .replaceAll("<br> <br>","\r\n")
                    .replaceAll("<br>","")
                    .replaceAll("&nbsp;","");
            txtAllPadd.append(result);
            txtAllPadd.append("===================");
             if(nextUrl.contains(".html")){
                 Thread.sleep(1000);
                 loadAllContent(nextUrl);
             }else{
                 fileWriter("D:\\MyText\\"+storyName + ".txt", txtAllPadd.toString());
             }
        }else{

            log.info("该网站页面参数有变动 ");
        }
    }

}
