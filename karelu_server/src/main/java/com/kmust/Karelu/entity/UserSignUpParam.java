package com.kmust.Karelu.entity;

import lombok.Data;

@Data
public class UserSignUpParam {

    private String username;

    private String icon;

    private String password;

    private String code;

    private String nickname;
}