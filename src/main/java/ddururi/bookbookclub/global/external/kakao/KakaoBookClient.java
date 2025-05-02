package ddururi.bookbookclub.global.external.kakao;

import ddururi.bookbookclub.domain.book.dto.KakaoBookSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class KakaoBookClient {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://dapi.kakao.com")
            .defaultHeader("Authorization", "KakaoAK 74987e6cb0f59e3fc4df5261a4bbb67f")
            .build();

    public KakaoBookSearchResponse searchBooks(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v3/search/book")
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .bodyToMono(KakaoBookSearchResponse.class)
                .block(); // 비동기 → 동기로 받음
    }
}
