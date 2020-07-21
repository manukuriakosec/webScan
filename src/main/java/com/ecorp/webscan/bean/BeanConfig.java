package com.ecorp.webscan.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecorp.webscan.repo.PageRepository;
import com.ecorp.webscan.service.PageService;
import com.ecorp.webscan.serviceImpl.PageServiceImpl;

@Configuration
public class BeanConfig {

    @Autowired
    private PageRepository pageRepository;


    @Bean
    public PageService pageService(){
        return new PageServiceImpl(pageRepository);
    }

}
