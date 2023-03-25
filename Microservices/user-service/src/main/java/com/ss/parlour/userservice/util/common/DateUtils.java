package com.ss.parlour.userservice.util.common;

import java.sql.Timestamp;

public class DateUtils {

    public static Timestamp currentSqlTimestamp(){
        long now = System.currentTimeMillis();
        return new Timestamp(now);
    }
}
