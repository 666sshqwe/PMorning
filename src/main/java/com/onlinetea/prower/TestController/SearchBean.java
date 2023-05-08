package com.onlinetea.prower.TestController;

import lombok.Data;

@Data
public class SearchBean {

    /** 网站地址 */
    String url;

    /** 小说第一章的url具体参数 */
    String inHtml;

    /** 页面元素ID */
    String requestID;

    /** 小说名 */
    String storyName;

}
