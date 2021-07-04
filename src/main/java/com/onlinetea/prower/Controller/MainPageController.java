package com.onlinetea.prower.Controller;


import com.alibaba.fastjson.JSONObject;
import com.onlinetea.prower.Bean.*;
import com.onlinetea.prower.Service.WxService;
import com.onlinetea.prower.Uitls.WebUtisServ;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller

public class MainPageController {
    @Autowired
    static
    WxService wxService;

    @Autowired
    static
    WebUtisServ webUtisServ;


    public static final String GET_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";


//
//    /**这样写的话，获取的地址栏上的请求，因为yml文件中配置了默认地址为/index，在
//     * /index后面加上/about，这个Getmapping就会获取到，然后定向about页面
//     * */
//    @GetMapping("/about")
//    public String index(HttpServletRequest request) {
//        return "about";
//    }

    /**进行接入验证*/
    @RequestMapping(value = "/mainpage",method =RequestMethod.GET)
    @ResponseBody
    public void DealMainPage(HttpServletRequest request,HttpServletResponse response) throws IOException {
        System.out.println("接入成功！！！");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        if(wxService.check(timestamp,nonce,signature)){
            System.out.println("验证成功！！！");
            PrintWriter out = response.getWriter();
            out.print(echostr);
            out.flush();
            out.close();
        }else{
            System.out.println("验证失败！！！");
        }
    }

    /**获取用户发送的信息*/
    @RequestMapping(value = "/mainpage",method =RequestMethod.POST)
    @ResponseBody
    public void doPost(HttpServletRequest request,HttpServletResponse response){
        System.out.println("dopost！！！");
//        getMenu();
//        wxService.getAccessToken();
    }



    /**生成自定义菜单*/
    private static  String getMenu(){
        Button btn = new Button();
        /**第一个一级菜单*/
        SubButton sb = new SubButton("体检预约");
        sb.getSub_button().add(new ViewButton("个人预约","http://47.97.122.233:28090/course/weChatPage/pageNav"));
        sb.getSub_button().add(new ViewButton("团体预约","http://47.97.122.233:28090/course/weChatPage/confirmappo"));
        btn.getButton().add(sb);
        /**第二个一级菜单*/
        btn.getButton().add(new ViewButton("体检报告","http://47.97.122.233:28090/course/weChatPage/newPage"));
        /**第三个一级菜单*/
        btn.getButton().add(new ViewButton("个人中心","http://47.97.122.233:28090/course/weChatPage/myself"));
        JSONObject.toJSON(btn);
        String url = GET_MENU_URL.replace("ACCESS_TOKEN",wxService.getAccessToken());
        String tokenStr = webUtisServ.getReDoPost(url,JSONObject.toJSON(btn).toString());
        System.out.println(tokenStr);
        return tokenStr;

    }



}
