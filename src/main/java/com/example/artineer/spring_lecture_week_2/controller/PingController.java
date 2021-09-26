package com.example.artineer.spring_lecture_week_2.controller;

import com.example.artineer.spring_lecture_week_2.dto.Response;
import com.example.artineer.spring_lecture_week_2.vo.ApiCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PingController {
    @GetMapping
    /*public String ping() {
        return "pong";
    }*/
    /*public Object ping() {
        return Map.of(
                "code", "0000",
                "desc", "정상입니다",
                "data", "pong"
        );
    }*/
    /*public Object ping() {
        return new Response(ApiCode.SUCCESS,  "pong");
    }*/

    public Response<String> ping() {
        return Response.<String>builder()
                .code(ApiCode.SUCCESS)
                .data("pong")
                .build();
    }

    /*public ResponseEntity<Response<String>> ping() {
        Response<String> response = Response.<String>builder()
                .code(ApiCode.SUCCESS).data("pong").build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }*/ // ResponseEntity 사용
}

/*class Obj {
    String param1;
    String param2;
    String param3;
    String param4;
    String param5;
    String param6;

    public Obj(String param1, String param2, String param3, String param4, String param5, String param6) {
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
        this.param5 = param5;
        this.param6 = param6;
    }
}*/

/*
class Process {
    public static void main(String[] args) {
        final Obj obj = new Obj("p1", "p2", "p3", "p4", "p5", "p6");
        Obj obj = new Obj("p1");

        // 10000 줄의 코드
        obj.setParam2("changed p2");

        return obj;  //return 값을 이해하기 위해서 수 많은 코드를 봐야 함.
    }
}*/
