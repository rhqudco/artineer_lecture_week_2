package com.example.artineer.spring_lecture_week_2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ArticleDto {
    @Getter
    public static class ReqPost {
        String title;
        String content;
        // 생성 요청할 때만 사용하는 객체로, 실제로 Service에서 Article로 변경되어 수행.
        // DTO와 Service는 구분되어 사용한다. Service는 종속되지 않고 공통적으로 사용되어야 하기 때문.
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Res {
        private String id;
        private String title;
        private String content;
    }
}
