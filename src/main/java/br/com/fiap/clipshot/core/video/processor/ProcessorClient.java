package br.com.fiap.clipshot.core.video.processor;

import br.com.fiap.clipshot.core.user.infraestructure.UserEntity;
import br.com.fiap.clipshot.core.video.Video;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ProcessorClient {

    @Value("${clipshot.token}")
    private String token;

    @Value("${clipshot.processor.url}")
    private String url;

    private final RestClient restClient;

    public ProcessorClient() {
        this.restClient = RestClient.builder().build();
    }

    public VideoProcessorResponse findAllBy(UserEntity user) {
        return this.restClient.get()
                .uri("%s/user/%s".formatted(url, user.getId()))
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(VideoProcessorResponse.class);
    }

    public VideoResponse findBy(Video video) {
        return this.restClient.get()
                .uri("%s/user/%s/video/%s".formatted(url, video.getUser().getId(), video.getId()))
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(VideoResponse.class);
    }
}
