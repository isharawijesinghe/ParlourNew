package com.ss.parlour.articleservice.utils.bean.response;

import com.ss.parlour.articleservice.utils.bean.CommentBean;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponse {

    private String parentCommentId;
    private int status;
    private String narration;
    private List<CommentBean> articleComments = new ArrayList<>();
}
