package com.example.artineer.spring_lecture_week_2.controller;

import com.example.artineer.spring_lecture_week_2.domain.Article;
import com.example.artineer.spring_lecture_week_2.dto.ArticleDto;
import com.example.artineer.spring_lecture_week_2.dto.Response;
import com.example.artineer.spring_lecture_week_2.service.ArticleService;
import com.example.artineer.spring_lecture_week_2.vo.ApiCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("api/v1/article") // 공통된 path값을 생략하게 해줌.
@RestController
public class ArticleController {
    private final ArticleService articleService;

    /*public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }*/ //lombok을 통해 생략 가능

    @PostMapping
    public Response<Long> post(@RequestBody ArticleDto.ReqPost request) {
        Article article = Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build(); // Domain을 DTO로 변환 하는 코드

        Long id = articleService.save(article); // Service에서 호출

        return Response.<Long>builder()
                .code(ApiCode.SUCCESS)
                .data(id)
                .build();
    }
    @GetMapping("/{id}")
    public Response<ArticleDto.Res> get(@PathVariable Long id) {
        Article article = articleService.findById(id); // Article 반환

        ArticleDto.Res response = ArticleDto.Res.builder()
                .id(String.valueOf(article.getId()))
                .title(article.getTitle())
                .content(article.getContent())
                .build(); // findById로 반환된 Article을 DTO로 변환

        return Response.<ArticleDto.Res>builder()
                .code(ApiCode.SUCCESS)
                .data(response)
                .build();
    }
}
