package com.ss.parlour.userservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@Table("userloginnameemailmapper")
public class UserLoginNameEmailMapper {


    private String loginName;
    @PrimaryKey
    private String email;

}
