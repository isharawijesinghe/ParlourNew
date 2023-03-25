package com.ss.parlour.streamservice.utils.common;

import java.sql.Timestamp;

public class DateUtils {

    public static Timestamp currentSqlTimestamp(){
        long now = System.currentTimeMillis();
        return new Timestamp(now);
    }
}
