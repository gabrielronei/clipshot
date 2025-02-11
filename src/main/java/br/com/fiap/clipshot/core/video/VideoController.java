package br.com.fiap.clipshot.core.video;

import br.com.fiap.clipshot.core.security.CurrentUser;
import br.com.fiap.clipshot.core.user.infraestructure.UserEntity;
import br.com.fiap.clipshot.core.video.upload.VideoUploadService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class VideoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoController.class);

    private final CurrentUser currentUser;
    private final VideoRepository videoRepository;
    private final VideoUploadService videoUploadService;

    public VideoController(CurrentUser currentUser,
                           VideoRepository videoRepository,
                           VideoUploadService videoUploadService) {
        this.currentUser = currentUser;
        this.videoRepository = videoRepository;
        this.videoUploadService = videoUploadService;
    }
    
    @InitBinder("newVideoForm")
    public void init(WebDataBinder binder) {
        binder.addValidators(new NewVideoFormValidator());
    }

    @PostMapping("/upload-videos")
    public ResponseEntity upload(@Valid NewVideoForm newVideoForm) {
        LOGGER.info("Started {} video(s) upload request", newVideoForm.getVideos().size());

        UserEntity user = currentUser.getPossibleUser().orElseThrow(() -> new IllegalStateException("Current user not Found"));

        List<Video> videos = videoRepository.saveAll(newVideoForm.toVideo(user));

        this.videoUploadService.upload(videos);

        return ResponseEntity.ok(videos.size());
    }

}
