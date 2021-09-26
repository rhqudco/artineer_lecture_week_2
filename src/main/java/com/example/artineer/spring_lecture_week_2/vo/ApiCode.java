package com.example.artineer.spring_lecture_week_2.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ApiCode {
    /* COMMON */
    SUCCESS("CM0000", "정상입니다.");

    private final String name; // code
    private final String desc; // desc

    ApiCode(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    /*public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }*/ // @Getter로 생략 가능
}

