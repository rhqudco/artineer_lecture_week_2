# artineer_spring_week_2

## Spring Web 진입점
- Controller는 Web의 진입점이고, 클라이언트의 요청을 받아들이는 컴포넌트다.

- @GetMapping 경로가 (“/“)인 경우 생략 가능.

- @Restcontroller : 해당 어노테이션은 REST-API의 형태로 웹의 진입점의 컨트롤러로 사용하겠다.

- API를 확인해 볼 수 있는 프로그램들 중 PostMan을 활용한다.
 - *실무에서는 Intelli J 내장 기능으로 사용*

### PingController.java
~~~java
    public Object ping() {
    return Map.of(
            "code", "0000",  /*code*/
            "desc", "정상입니다", /*설명*/
            "data", "pong" /*값*/
   );
    }
~~~
- 실제 API를 만들 때 data를 제외하고, API Code에 대한 값과 Code에 대한 설명이 들어가야 한다.

- Map 타입으로 작성했지만 응답은 JSON 방식으로 응답한다.

- 자바에서 Map을 통해 생성한 객체는 JS나 TS에서 익명으로 만든 객체와 동일한데, 위 코드는 응답객체로 정의하고 익명객체로 사용 중.

- 위와 같은 코드로 실제 API를 작성하게 된다면 응답코드를 만든지 파악하기 어려워 비효율적이기 때문에 하나의 Class로 따로 작성하는 것이 효율적.

### Response.java
~~~java
    public class Response {
    private String code;
    private String desc;
    private String data;

    public Response(String code, String desc, String data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    }
~~~
위의 Class를 통해 아래와 같은 코드로 변환하여 중복되는 코드 작성을 줄여줄 수 있다.

### PingController.java 수정
~~~java
    public Object ping() {
    return new Response("0000", "정상입니다", "pong"); // 순서대로 code, desc, data
    }
~~~
- 하지만 다른 곳에서 수정이 발생하면 연쇄적으로 수정을 해야 한다는 이슈가 발생.

- 이를 개선하여 

### ApiCode.java
~~~java
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
    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
    }
~~~

### PingController.java
~~~java
    public Object ping() {
    return new Response(ApiCode.SUCCESS,  "pong");
    }
~~~
- 와 같은 코드로 작성할 수 있다.

- Postman에서 확인시 원하는 값이 나오지 않는다. enum 특성상 객체의 이름을 사용하여 속성값을 보여주지 않는다.

- 이를 해결하기 위해 @JsonFormat(shape = JsonFormat.Shape.OBJECT)을 사용하여 JSON으로 변환.
  - *jackson 라이브러리를 사용. jackson 라이브러리는 Java에서 JSON이나 XML의 데이터 구조를 Parsing해준다.*

## 빌더패턴
- 파라미터를 구분하는 수단이 순서 뿐일 때 가독성이 떨어지고 어떤 파라미터인지 구분하기 어렵다.

- SETTER를 사용할 수 있지만 불변성을 유지하기 어려워진다.

- 또한 의존성을 부분적으로 주입할 때 그에 해당하는 생성자를 또 만들어줘야 하는 불편함이 생긴다.

- 빌더패턴을 사용하여 이 문제들을 해결할 수 있다.

### Response.java
~~~java
    public class Response {
    private ApiCode code;
    private String data;

    private Response() {}

    public Response(ApiCode code, String data) {
        this.code = code;
        this.data = data;
    }

    public ApiCode getCode() {
        return code;
    }

    public void setCode(ApiCode code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static class Builder  {
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
    }
   }
~~~

### PingController.java
~~~ java
    @RestController
    public class PingController {
    @GetMapping
       public Object ping() {
        return Response.builder()
                .code(ApiCode.SUCCESS)
                .data("pong")
                .build();
    }
  }
~~~
- 각 파라미터에 어떤 값을 넣는지 명시 되어 있으며 생성자를 많이 만들어야 하는 이슈가 발생하지 않고 불변 하다는 장점이 있다.

## Lombok
- lombok을 통해 보일러플레이트 코드를 정리할 수 있다.
~~~java
    dependencies {
    // ...
	compileOnly 'org.projectlombok:lombok:1.18.20'
	annotationProcessor 'org.projectlombok:lombok:1.18.20'
    // ...
    } // 의존성 추가
~~~
- @Getter : 어노테이션을 작성하여 Getter를 생략할 수 있다.

- @Builder : 어노테이션을 작성하여 Builder 패턴을 구현할 수 있다.

### Response.java
~~~java
    @Getter
    @Builder
    public class Response {
    private ApiCode code;
    private String data;
    }
~~~

### ApiCode.java
~~~java
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
 }
~~~
## 동적 타입
- 사실상 API통신이나 Stream통신 등 실제로 주고 받는건 문자열이다. 

- String을 사용해도 괜찮지만, 최종적으로 API를 내려줄 때 String으로 변환해야 하는 이슈가 발생한다. 

- 이를 해결하기 위해 동적 타입을 사용한다.

### Response.java
~~~java
    @Getter
    @Builder
    public class Response<T> {
    private ApiCode code;
    private T data;
    }
~~~

### PingController.java
~~~java
    public Response<String> ping() {
        return Response.<String>builder()
                .code(ApiCode.SUCCESS)
                .data("pong")
                .build();
    }
 }
~~~

## ResponseEntity
- Http Status Code에서 OK를 나타내는 코드가 응답 되어야만 결과를 볼 수 있었다.

- Spring에서 제공하는 ResponseEntity를 사용하게 된다면 Header값을 조작할 수 있다.

### PingController.java
~~~java
    public ResponseEntity<Response<String>> ping() {
    Response<String> response = Response.<String>builder()
            .code(ApiCode.SUCCESS).data("pong").build();

    return ResponseEntity
            .status(HttpStatus.OK)
            .body(response);
    }
~~~

## REST-API
- CRUD는 REST-API를 만들 때 가장 많이 사용하는 단어

- C : Create, R  : Read, U : Update, D : Delete

- REST-API는 표준이 없다고 하지만 보편적으로 Http 메소드로 표현한다.

- GET - 조회

- POST - 생성

- PUT - 변경

- DELETE - 제거

- REST-API는 클라이언트의 요구에 맞는 API가 아니고 domain을 중심으로 만든 API이다.

- System 중심으로 작성한 API이기 때문에 범용성 있게 활용할 수 있다.

## Service
- Service는 Spring의 핵심 요소로 비즈니스 로직을 담는 곳, 범용적으로 사용될 수 있는 기능을 담는 곳이다. 

- Controller에 담지 않는 이유는 특정 기능을 특정 Controller에 담게 됐을 때, 다른 Controller에서 특정 기능이 필요할 때 특정 Controller에 종속될 수 있다. 

- 결국 Controller는 Service에게 제공 받아 액션을 취한다.

- @Serivce 어노테이션도 마찬가지로 Bean에 등록.

### Article.java
  ~~~java
    @Getter
    @Builder
    public class Article {
    Long id;
    String title;
    String content;
    }
~~~
### ArticleService.java
  ~~~java
    @Service
    public class ArticleService {
    private Long id = 0L;
    final List<Article> database = new ArrayList<>();

    private Long getId() {
        return ++id;
    }

    public Long save(Article request) { //POST기능 (생성)
        Article domain = Article.builder()
                .id(getId())
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        database.add(domain);
        return domain.getId();
    }
    }
 ~~~
### ArticleController.java
  ~~~java
    @RequiredArgsConstructor
    @RequestMapping("api/v1/article") // 공통된 path값을 생략하게 해줌.
    @RestController
    public class ArticleController {
    private final ArticleService articleService;

    /*public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }*/ //lombok을 통해 생략 가능
    }
~~~
## DTO
- Domain(객체지향적으로 설계 상속하고, 다형성을 지켜서 만든다. (Article.java))의 영역이 API로 내려갈 때는 Presentation의 영역으로 바꿔서 내려가야 한다. 

- 표현의 영역에 있는 것이 DTO다.

- 실제 화면에 보여질 때는 객체로 표현하기 어려운 부분을 해결해준다.

- DTO는 생성 요청할 때만 사용하는 객체로, 실제로 Service에서 Article로 변경되어 수행.

- DTO와 Service는 구분되어 사용한다. Service는 종속되지 않고 공통적으로 사용 되어야 하기 때문.

- Domain 레이어와 Presentation 레이어가 구분되어야 하고 구분하기 위해 Presenstation 영역에 사용하는 객체가 DTO다.

## POST

    ### ArticleDto.java
~~~java  
    public class ArticleDto {
    @Getter
    public static class ReqPost {
        String title;
        String content;
        // 생성 요청할 때만 사용하는 객체로, 실제로 Service에서 Article로 변경되어 수행.
        // DTO와 Service는 구분되어 사용한다. Service는 종속되지 않고 공통적으로 사용되어야 하기 때문.
    }
~~~

### ArticleController.java
  ~~~java
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
    }
~~~
## GET

### ArticleService.java
  ~~~java
    public class ArticleService {
    //...
  
      public Article findById(Long id) {
    return database.stream().filter(article -> article.getId().equals(id)).findFirst().get();
    }   
  
    // ...
    }
~~~
### ArticleDto.java
  ~~~java
    public class ArticleDto {
    //…
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
~~~
### ArticleController.java
 ~~~java
    @RequiredArgsConstructor
    @RequestMapping("api/v1/article") // 공통된 path값을 생략하게 해줌.
    @RestController
    public class ArticleController {
    //…
    @GetMapping("/{id}")
    public Response<ArticleDto.Res> get(@PathVariable Long id) {
        Article article = articleService.findById(id);

        ArticleDto.Res response = ArticleDto.Res.builder()
                .id(String.valueOf(article.getId()))
                .title(article.getTitle())
                .content(article.getContent())
                .build(); // DTO로 변환

        return Response.<ArticleDto.Res>builder()
        .code(ApiCode.SUCCESS)
        .data(response)
        .build();
    }
    }
~~~
