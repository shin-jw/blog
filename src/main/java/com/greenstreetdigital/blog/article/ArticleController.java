package com.greenstreetdigital.blog.article;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Configuration
public class ArticleController {

    @Bean
    RouterFunction<?> routes(RequestHandler requestHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/articles"), requestHandler::getArticles)
                .andRoute(RequestPredicates.GET("/articles/{id}"), requestHandler::getArticle);
    }

    @Component
    @RequiredArgsConstructor
    public static class RequestHandler {

        private final ArticleService articleService;

        public Mono<ServerResponse> getArticles(ServerRequest serverRequest) {
            return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                    .body(articleService.getArticles(), ArticleResponse.class);

        }


        public Mono<ServerResponse> getArticle(ServerRequest serverRequest) {

            return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                    .body(fromValue(serverRequest.pathVariable("id")));
        }

    }
}
