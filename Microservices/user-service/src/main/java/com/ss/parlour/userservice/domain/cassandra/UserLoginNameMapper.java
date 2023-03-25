package com.ss.parlour.userservice.domain.cassandra;

import com.ss.parlour.userservice.util.bean.requests.UserRegisterRequestBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("user_login_name_mapper")
public class UserLoginNameMapper {

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String loginName;
    private String userId;

    public UserLoginNameMapper(UserRegisterRequestBean.UserRegisterRequestInnerBean userRegisterRequestInnerBean){
        this.loginName = userRegisterRequestInnerBean.getLoginName();
        this.userId = userRegisterRequestInnerBean.getUserId();
    }

    public UserLoginNameMapper(User user){
        this.loginName = user.getLoginName();
        this.userId = user.getUserId();
    }


}
