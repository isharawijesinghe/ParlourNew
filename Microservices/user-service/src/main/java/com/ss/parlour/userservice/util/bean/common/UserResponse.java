package com.ss.parlour.userservice.util.bean.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

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
