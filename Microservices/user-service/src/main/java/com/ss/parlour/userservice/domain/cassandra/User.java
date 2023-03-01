package com.ss.parlour.userservice.domain.cassandra;

import com.ss.parlour.userservice.util.bean.AuthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Table("user")
public class User implements UserDetails {

    @PrimaryKey
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
