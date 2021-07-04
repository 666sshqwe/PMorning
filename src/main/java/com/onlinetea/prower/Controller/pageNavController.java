package com.onlinetea.prower.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/weChatPage",method = RequestMethod.POST)
public class pageNavController {

    @GetMapping("/pageNav")
    public String pageNav(HttpServletRequest request) {

        return "pageNav";
    }

    @GetMapping("/appo")
    public String appo(HttpServletRequest request) {

        return "appo";
    }

    @GetMapping("/combo")
    public String combo(HttpServletRequest request) {

        return "combo";
    }

    @GetMapping("/confirmappo")
    public String confirmappo(HttpServletRequest request) {

        return "confirmappo";
    }

    @GetMapping("/meCen")
    public String meCen(HttpServletRequest request) {

        return "meCen";
    }

    @GetMapping("/success")
    public String success(HttpServletRequest request) {

        return "success";
    }


    @GetMapping("/medicReport")
    public String medicReport(HttpServletRequest request) {

        return "medicReport";
    }


    @GetMapping("/myself")
    public String myself(HttpServletRequest request) {

        return "myself";
    }


    @GetMapping("/editmyself")
    public String editmyself(HttpServletRequest request) {

        return "editmyself";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request) {

        return "login";
    }

    @GetMapping("/newPage")
    public String newPage(HttpServletRequest request) {

        return "newPage";
    }

    @GetMapping("/teamappo")
    public String teamappo(HttpServletRequest request) {

        return "teamappo";
    }

    @GetMapping("/teaminfo")
    public String teaminfo(HttpServletRequest request) {

        return "teaminfo";
    }
}
