package org.spotifyapi.spotify.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SpotifyConfig {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    private final String TOKEN_URL = "https://accounts.spotify.com/api/token";

    // 토큰과 만료 시간을 저장할 변수
    private String accessToken;
    private Instant tokenExpirationTime;

    public String getAccessToken() {
        // 현재 시간이 토큰 만료 시간 이후라면 새로운 토큰을 요청
        if (accessToken == null || Instant.now().isAfter(tokenExpirationTime)) {
            refreshAccessToken();
        }
        return accessToken;
    }

    // 토큰을 새로 발급받는 메서드
    private void refreshAccessToken() {
        RestTemplate restTemplate = new RestTemplate();

        // Client Credentials
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + encodedCredentials);

        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);

        ResponseEntity<String> response = restTemplate.postForEntity(TOKEN_URL, request, String.class);

        // 응답이 성공적이면, 토큰을 저장하고 만료 시간을 설정
        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            accessToken = extractAccessToken(responseBody);
            // 만료 시간은 1시간 후로 설정
            tokenExpirationTime = Instant.now().plusSeconds(3600);
        } else {
            throw new RuntimeException("Failed to retrieve access token");
        }
    }

    private String extractAccessToken(String responseBody) {
        // JSON에서 액세스 토큰을 추출하는 부분
        return responseBody.split("\"access_token\":\"")[1].split("\"")[0];
    }
}
