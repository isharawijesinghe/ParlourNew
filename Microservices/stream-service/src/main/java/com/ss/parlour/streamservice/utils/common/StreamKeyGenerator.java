package com.ss.parlour.streamservice.utils.common;

import com.datastax.driver.core.utils.UUIDs;
import com.ss.parlour.streamservice.utils.bean.StreamConst;
import org.springframework.stereotype.Component;

@Component
public class StreamKeyGenerator {

    public String streamKeyGenerator(String userName){
        return StreamConst.STREAM_TYPE + "_" + userName + "_" +  UUIDs.timeBased();
    }
}
