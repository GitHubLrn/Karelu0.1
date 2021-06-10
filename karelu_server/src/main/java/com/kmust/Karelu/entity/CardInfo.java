package com.kmust.Karelu.entity;

import lombok.Data;

@Data
public class CardInfo {

    private String signature;
    private int focuscount;
    private int befocusedcount;
    private String[] isfocused;
    private String un;
    private Long uid;

}
