package com.ss.parlour.articleservice.domain.cassandra;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashMap;

@Table("editrequestbyuser")
@Getter
@Setter
@NoArgsConstructor
public class EditRequestByUser {

    @PrimaryKey
    private String userId;
    private HashMap<String, EditRequest> editRequestByUserMap = new HashMap<>();

    public EditRequestByUser(String userId, HashMap<String, EditRequest> editRequestByUserMap){
        this.userId = userId;
        this.editRequestByUserMap = editRequestByUserMap;
    }

}
