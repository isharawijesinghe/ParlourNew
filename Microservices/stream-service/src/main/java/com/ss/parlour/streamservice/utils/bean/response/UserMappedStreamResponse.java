package com.ss.parlour.streamservice.utils.bean.response;

import com.ss.parlour.streamservice.domain.cassandra.StreamByUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserMappedStreamResponse {

    private List<StreamByUser> streamByUser;

}
