package com.ss.parlour.userservice.domain.cassandra;

import com.ss.parlour.userservice.util.bean.requests.UserRegisterRequestBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("user_login_email_mapper")
public class UserLoginEmailMapper {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String email;
    private String userId;


    public UserLoginEmailMapper(UserRegisterRequestBean.UserRegisterRequestInnerBean userRegisterRequestInnerBean){
        this.email = userRegisterRequestInnerBean.getEmail();
        this.userId = userRegisterRequestInnerBean.getUserId();
    }

    public UserLoginEmailMapper(User user){
        this.email = user.getEmail();
        this.userId = user.getUserId();
    }

}
