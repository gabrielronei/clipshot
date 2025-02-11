package br.com.fiap.clipshot.core.video;

import br.com.fiap.clipshot.core.user.infraestructure.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class VideoTest {

    @Test
    void shouldReturnAnVideoWithAllAttributes() {
        UserEntity userEntity = new UserEntity();
        MockMultipartFile file = new MockMultipartFile("afile", new byte[0]);
        Video video = new Video(file, userEntity);

        Assertions.assertThat(video.getId()).isNull();
        Assertions.assertThat(video.getTitle()).isBlank();
        Assertions.assertThat(video.getCreatedAt()).isNotNull();
        Assertions.assertThat(video.getStatus()).isEqualTo(Video.Status.PENDING);
        Assertions.assertThat(video.getFile().get()).isEqualTo(file);
        Assertions.assertThat(video.getUser()).isEqualTo(userEntity);

    }
}