package org.spotifyapi.spotify.api.dto.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
public class SpotifyResponseDto {
    private ArtistResponse artists;

    // from 메서드 추가
    public static SpotifyResponseDto from(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, SpotifyResponseDto.class);
    }

    @Getter
    @Setter
    public static class ArtistResponse {
        private String href;
        private List<ArtistDto> items;
        private int limit;
        private String next;
        private int offset;
        private String previous;
        private int total;
    }

    @Getter
    @Setter
    public static class ArtistDto {
        private ExternalUrls external_urls;
        private Followers followers;
        private List<String> genres;
        private String href;
        private String id;
        private List<Image> images;
        private String name;
        private int popularity;
        private String type;
        private String uri;

        @Getter
        @Setter
        public static class ExternalUrls {
            private String spotify;
        }

        @Getter
        @Setter
        public static class Followers {
            private String href;
            private int total;
        }

        @Getter
        @Setter
        public static class Image {
            private String url;
            private int height;
            private int width;
        }
    }
}
