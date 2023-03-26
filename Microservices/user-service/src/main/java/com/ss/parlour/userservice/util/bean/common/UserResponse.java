package com.ss.parlour.userservice.util.bean.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserResponse <T> extends UserHeader{

    private String message;
    private int httpStatus;
    private ZonedDateTime zonedDateTime;
    private T body;
}
