package com.ss.parlour.articleservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashMap;

@Table("edit_request_by_user")
@Getter
@Setter
@NoArgsConstructor
public class EditRequestByUser {

    @PrimaryKey
    private String userId;
    private HashMap<String, EditRequest> editRequestByUserMap = new HashMap<>();

}
