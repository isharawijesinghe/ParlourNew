package com.ss.parlour.streamservice.utils.bean.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StreamCreateResponse {

    private String streamId;
    private int status;
    private String narration;
}
