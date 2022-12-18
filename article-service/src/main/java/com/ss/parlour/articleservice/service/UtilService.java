package com.ss.parlour.articleservice.service;

import com.ss.parlour.articleservice.handler.image.ImageHandlerI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilService implements UtilServiceI {

    @Autowired
    private ImageHandlerI imageHandlerI;

    @Override
    public String generatePreSignUrl(){
        return imageHandlerI.generatePreSignUrl();
    }
}
