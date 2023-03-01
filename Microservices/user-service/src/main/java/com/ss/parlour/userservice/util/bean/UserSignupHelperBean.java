package com.ss.parlour.userservice.util.bean;

import com.ss.parlour.userservice.domain.cassandra.User;
import com.ss.parlour.userservice.domain.cassandra.UserLoginNameEmailMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignupHelperBean {

    private User user;
    private UserLoginNameEmailMapper loginNameEmailMapper;
}
