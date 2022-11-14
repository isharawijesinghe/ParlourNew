package com.ss.parlour.authorizationservice.dao;

import com.ss.parlour.authorizationservice.domain.User;

public interface UserDAOI {

    User getUser(String userIdentification);
}
