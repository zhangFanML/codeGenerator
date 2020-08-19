package com.git.easyloan.controller;

import com.git.easyloan.entity.PageData;
import com.git.easyloan.utils.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends BaseController {

    @GetMapping
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest req){
        String login = req.getHeader("Upgrade-Insecure-Requests");
        if (login != null && login != ""){
            if (login.equalsIgnoreCase("1")){
                return "index/main";
            }
        }
        return "index";

    }

    /**
     * 进入tab标签
     * @return
     */
    @RequestMapping(value="/tab")
    public String tab(){
        return "index/tab";
    }

    /**
     * 进入首页后的默认页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/login_default")
    public ModelAndView defaultPage(){
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd.put("userCount", 10);				//系统用户数
        pd.put("appUserCount", 2);	//会员数
        mv.addObject("pd",pd);
        mv.setViewName("index/default");
        return mv;
    }
}
