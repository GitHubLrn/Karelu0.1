package com.kmust.Karelu.entity;

import lombok.Data;

@Data
public class UserLoginParam {

    private String username;

    private String password;

    private String code;
}
