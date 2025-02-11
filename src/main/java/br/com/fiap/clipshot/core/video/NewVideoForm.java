package br.com.fiap.clipshot.core.video;

import br.com.fiap.clipshot.core.user.infraestructure.UserEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class NewVideoForm {

    @NotNull
    @Size(min = 1, max = 3)
    public List<MultipartFile> videos = new ArrayList<>();

    public NewVideoForm(List<MultipartFile> videos) {
        this.videos = videos;
    }

    public List<MultipartFile> getVideos() {
        return videos;
    }

    public List<Video> toVideo(UserEntity user) {
        return videos.stream().map(video -> new Video(video, user)).toList();
    }
}
