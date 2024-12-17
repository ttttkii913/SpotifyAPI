package org.spotifyapi.spotify.api;

import lombok.RequiredArgsConstructor;
import org.spotifyapi.spotify.api.dto.response.SpotifyResponseDto;
import org.spotifyapi.spotify.application.SpotifyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SpotifyController {

    private final SpotifyService spotifyService;

    @GetMapping("api/spotify/search")
    public SpotifyResponseDto searchSpotify(@RequestParam("query") String query, @RequestParam("type") String type) {
        return spotifyService.searchSpotify(query, type);
    }
}
