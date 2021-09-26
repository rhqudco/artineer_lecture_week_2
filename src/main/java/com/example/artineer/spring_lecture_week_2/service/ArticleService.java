package com.example.artineer.spring_lecture_week_2.service;

import com.example.artineer.spring_lecture_week_2.domain.Article;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService { // 공통의 비즈니스 로직을 처리하는 클래스
    private Long id = 0L;
    final List<Article> database = new ArrayList<>();

    private Long getId() {
        return ++id;
    }

    public Long save(Article request) { //POST기능 (생성) -> DB에 넣어줌
        Article domain = Article.builder()
                .id(getId())
                .title(request.getTitle())
                .content(request.getContent())
                .build(); // 모든 Controller에게 종속적이면 안되기 때문에 Domain을 사용 DTO를 사용하지 않음.

        database.add(domain);
        return domain.getId();
    }
    public Article findById(Long id) { // GET(조회)
        return database.stream().filter(article -> article.getId().equals(id)).findFirst().get();
    }
}
