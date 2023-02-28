package com.ss.parlour.userservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("user_info")
@Getter
@Setter
@NoArgsConstructor
public class UserInfo {

    @PrimaryKey
    private String loginName;
    private String firstName;
    private String lastName;
    private String country;
    private String jobTitle;
    private String company;
    private String experience;
    private String profileImage;
    private String description;
}
