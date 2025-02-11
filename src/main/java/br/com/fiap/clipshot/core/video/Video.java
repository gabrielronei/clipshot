package br.com.fiap.clipshot.core.video;

import br.com.fiap.clipshot.core.user.infraestructure.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @PastOrPresent
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @NotBlank
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String url;

    private transient MultipartFile file;

    @Deprecated
    public Video() {
    }

    public Video(MultipartFile file, UserEntity user) {
        this.title = file.getOriginalFilename();
        this.file = file;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public UserEntity getUser() {
        return user;
    }

    public Optional<MultipartFile> getFile() {
        return Optional.ofNullable(file);
    }

    public Video update(String videoUrl) {
        this.url = videoUrl;
        this.updatedAt = LocalDateTime.now();

        return this;
    }

    public boolean isInvalid() {
        return this.status.isInvalid();
    }

    public Video markAsError() {
        this.status = Status.ERROR;
        this.updatedAt = LocalDateTime.now();

        return this;
    }

    enum Status {
        EXPIRED,
        ERROR,
        PENDING;

        public boolean isInvalid() {
            return Arrays.asList(EXPIRED, ERROR).contains(this);
        }
    }
}
