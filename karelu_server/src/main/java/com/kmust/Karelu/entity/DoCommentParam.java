package com.kmust.Karelu.entity;

import lombok.Data;

@Data
public class DoCommentParam {

    private String userid;

    private Integer noteid;

    private String commentthing;

    private int fatherid;
}
