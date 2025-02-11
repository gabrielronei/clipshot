package br.com.fiap.clipshot.core.video.processor;

import br.com.fiap.clipshot.core.security.CurrentUser;
import br.com.fiap.clipshot.core.user.infraestructure.UserEntity;
import br.com.fiap.clipshot.core.video.Video;
import br.com.fiap.clipshot.core.video.VideoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController("/processor")
public class VideoProcessorController {

    private final CurrentUser currentUser;
    private final ProcessorClient processorClient;
    private final VideoRepository videoRepository;

    public VideoProcessorController(CurrentUser currentUser,
                                    ProcessorClient processorClient,
                                    VideoRepository videoRepository) {
        this.currentUser = currentUser;
        this.processorClient = processorClient;
        this.videoRepository = videoRepository;
    }

    @GetMapping("/list")
    public ResponseEntity list() {
        UserEntity currentUser = this.currentUser.getPossibleUser()
                .orElseThrow(() -> new IllegalStateException("Possible user not found"));

        return ResponseEntity.ok(processorClient.findAllBy(currentUser));
    }

    @GetMapping("/video-id/{videoId}")
    public ResponseEntity getOne(@PathVariable UUID videoId) {
        UserEntity currentUser = this.currentUser.getPossibleUser()
                .orElseThrow(() -> new IllegalStateException("Possible user not found"));

        Video video = videoRepository.findByIdAndUserId(videoId, currentUser.getId())
                .orElseThrow(() -> new IllegalStateException("Video not found"));

        if (video.isInvalid()) {
            return ResponseEntity.badRequest().body("Video is invalid to download because it's already expired or has error");
        }

        return ResponseEntity.ok(processorClient.findBy(video));
    }

}
