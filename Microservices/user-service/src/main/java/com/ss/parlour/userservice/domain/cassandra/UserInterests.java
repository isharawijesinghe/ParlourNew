package com.ss.parlour.userservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table("user_interests")
public class UserInterests {

    private String loginName;
    private List<Topics> userInterests;
}
