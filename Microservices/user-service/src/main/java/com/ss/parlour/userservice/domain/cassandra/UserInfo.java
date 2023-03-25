package com.ss.parlour.userservice.domain.cassandra;

import com.ss.parlour.userservice.util.bean.requests.UserInfoUpdateRequestBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("user_info")
@Getter
@Setter
@NoArgsConstructor
public class UserInfo {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String userId;
    private String firstName;
    private String lastName;
    private String country;
    private String jobTitle;
    private String company;
    private String experience;
    private String profileImage;
    private String description;

    public UserInfo(UserInfoUpdateRequestBean.UserInfoUpdateInnerRequestBean userInfoInner){
        this.userId = userInfoInner.getLoginName();
        this.firstName = userInfoInner.getFirstName();
        this.lastName = userInfoInner.getLastName();
        this.country = userInfoInner.getCountry();
        this.jobTitle = userInfoInner.getJobTitle();
        this.company = userInfoInner.getCompany();
        this.experience = userInfoInner.getExperience();
        this.profileImage = userInfoInner.getProfileImage();
        this.description = userInfoInner.getDescription();
    }
}
