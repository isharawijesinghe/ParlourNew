package com.ss.parlour.streamservice.utils.bean;

import com.ss.parlour.streamservice.domain.cassandra.Stream;
import com.ss.parlour.streamservice.domain.cassandra.StreamByUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StreamDeleteHelperBean {

    private Stream stream;
    private StreamByUser streamByUser;
}
