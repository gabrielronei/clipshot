package br.com.fiap.clipshot.core.video.processor;

import br.com.fiap.clipshot.core.user.infraestructure.UserEntity;
import br.com.fiap.clipshot.core.video.Video;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ProcessorClient {
    private final RestClient restClient;

    public ProcessorClient() {
        this.restClient = RestClient.builder().build();
    }

    public VideoProcessorResponse findAllBy(UserEntity user) {
        return this.restClient.get().uri("/user/%s".formatted(user.getId())).retrieve().body(VideoProcessorResponse.class);
    }

    public VideoResponse findBy(Video video) {
        return this.restClient.get().uri("/user/%s/video/%s".formatted(video.getUser().getId(), video.getId())).retrieve().body(VideoResponse.class);
    }
}
