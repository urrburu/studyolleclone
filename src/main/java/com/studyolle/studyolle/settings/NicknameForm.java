package com.studyolle.studyolle.settings;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class NicknameForm {

    @NotBlank
    @Length(min =3, max =30)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,30}$")
    private String nickname;
}
