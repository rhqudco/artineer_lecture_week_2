package com.example.artineer.spring_lecture_week_2.dto;

import com.example.artineer.spring_lecture_week_2.vo.ApiCode;

import lombok.Builder;
import lombok.Getter;

import java.nio.Buffer;

@Getter
@Builder
public class Response<T> {
    private ApiCode code;
    private T data;

    /*private Response() {}*/ // Builder 생략

    /*public Response(ApiCode code, String data) {
        this.code = code;
        this.data = data;
    }*/ // 생성자 만들 때 사용했던 코드 생략 가능

    /*
    public ApiCode getCode() {
        return code;
    }
    public String getData() {
        return data;
    }
    */ //lombok을 통해 Getter 어노테이션하여 생략 가능.

    /*public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Response response;

        public Builder() {
            this.response = new Response();
        }

        public Builder code(ApiCode code) {
            this.response.code = code;
            return this;
        }

        public Builder data(String data) {
            this.response.data = data;
            return this;
        }

        public Response build() {
            return this.response;
        }
    }*/ //lombok을 통해 빌더패턴 생성
}