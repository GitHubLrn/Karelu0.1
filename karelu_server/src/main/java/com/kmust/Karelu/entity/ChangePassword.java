package com.kmust.Karelu.entity;

import lombok.Data;

@Data
public class ChangePassword {
    String newpassword;
    String oldpassword;
    int uid;
}
