package org.spotifyapi.spotify.application;

import lombok.RequiredArgsConstructor;
import org.spotifyapi.spotify.api.dto.response.SpotifyResponseDto;
import org.spotifyapi.spotify.config.SpotifyConfig;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SpotifyService {

    private final SpotifyConfig spotifyConfig;

    private final String BASE_API_URL = "https://api.spotify.com/v1";

    public SpotifyResponseDto searchSpotify(String query, String type) {
        String accessToken = spotifyConfig.getAccessToken();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = BASE_API_URL + "/search?q=" + query + "&type=" + type +"&limit=1";

        // 응답을 문자열로 받아오고, 이후 DTO로 변환
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // JSON 응답을 SpotifyResponseDto로 변환하여 리턴
        try {
            return SpotifyResponseDto.from(response.getBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
