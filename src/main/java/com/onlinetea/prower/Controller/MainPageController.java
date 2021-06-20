package com.onlinetea.prower.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainPageController {
//
//    /**这样写的话，获取的地址栏上的请求，因为yml文件中配置了默认地址为/index，在
//     * /index后面加上/about，这个Getmapping就会获取到，然后定向about页面
//     * */
//    @GetMapping("/about")
//    public String index(HttpServletRequest request) {
//        return "about";
//    }

    @RequestMapping("/about")
    @ResponseBody
    public Object DealMainPage(Model model){

        return "course";
    }

}
