package com.ss.parlour.streamservice.utils.bean.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StreamDeleteResponse {

    private String streamId;
    private int status;
    private String narration;

}
