package com.ss.parlour.userservice.domain.cassandra;

import com.ss.parlour.userservice.configurations.security.oauth2.user.OAuth2UserInfo;
import com.ss.parlour.userservice.util.bean.AuthProvider;
import com.ss.parlour.userservice.util.bean.UserConst;
import com.ss.parlour.userservice.util.bean.requests.UserRegisterRequestBean;
import com.ss.parlour.userservice.util.common.DateUtils;
import com.ss.parlour.userservice.util.common.TokenGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.sql.Timestamp;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Table("user")
public class User implements UserDetails {

    @Autowired
    private TokenGenerator tokenGenerator;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String userId;
    private String loginName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean locked = false;
    private Boolean enabled = false;
    private Timestamp createdDate;
    private Timestamp lastUpdatedDate;
    private String activeToken;
    @CassandraType(type = CassandraType.Name.TEXT)
    private AuthProvider provider;
    private String imageUrl;
    private String providerId;

    public User(UserRegisterRequestBean.UserRegisterRequestInnerBean userRegisterRequestInnerBean, boolean isEmailVerificationRequired){
        this.userId = userRegisterRequestInnerBean.getUserId();
        this.firstName = userRegisterRequestInnerBean.getFirstName();
        this.lastName = userRegisterRequestInnerBean.getLastName();
        this.loginName = userRegisterRequestInnerBean.getLoginName();
        this.email = userRegisterRequestInnerBean.getEmail();
        this.password = userRegisterRequestInnerBean.getPassword();
        this.createdDate = DateUtils.currentSqlTimestamp();
        this.lastUpdatedDate = DateUtils.currentSqlTimestamp();
        this.enabled = !isEmailVerificationRequired ? UserConst.TRUE : UserConst.FALSE;
        this.activeToken = userRegisterRequestInnerBean.getToken();
        this.provider = AuthProvider.local;
    }

    public User(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo){
        this.userId = tokenGenerator.generateUniqueUserId();
        this.loginName = oAuth2UserInfo.getEmail();
        this.provider = AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId());
        this.providerId = oAuth2UserInfo.getId();
        this.firstName = oAuth2UserInfo.getName();
        this.email = oAuth2UserInfo.getEmail();
        this.imageUrl = oAuth2UserInfo.getImageUrl();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
