package com.git.easyloan.utils.base;


import com.git.easyloan.entity.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public class BaseController extends BaseMessage {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private static final long serialVersionUID = 6357869213649815390L;
    public static final String DICMAP = "dicMap";

    public BaseController() {
    }

    public PageData getPageData() {
        return new PageData(this.getRequest());
    }

    public ModelAndView getModelAndView() {
        return new ModelAndView();
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public String get32UUID() {
        return KeyGenerator.get32UUID();
    }
}
