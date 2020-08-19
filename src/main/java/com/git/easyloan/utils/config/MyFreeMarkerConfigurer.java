package com.git.easyloan.utils.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Configuration
public class MyFreeMarkerConfigurer extends FreeMarkerConfigurer {


    public MyFreeMarkerConfigurer(){
        super.setTemplateLoaderPaths("classpath:ftl/createCode","classpath:ftl/createFaCode","classpath:ftl/createSoCode","classpath:ftl/createTreeCode","classpath:ftl/createDicCode");
    }

}
