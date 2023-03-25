package com.ss.parlour.userservice.util.bean;

import com.ss.parlour.userservice.domain.cassandra.UserInterests;
import com.ss.parlour.userservice.domain.cassandra.UserInterestsByUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserInterestsAddHelperBean {

    private List<UserInterests> listOfUserInterests = new ArrayList<>();
    private List<UserInterestsByUser> listOfUserInterestsByUser = new ArrayList<>();
}
