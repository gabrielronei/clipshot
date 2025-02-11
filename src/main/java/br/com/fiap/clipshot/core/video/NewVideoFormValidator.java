package br.com.fiap.clipshot.core.video;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class NewVideoFormValidator implements Validator {
    private final String VIDEO_CONTENT_TYPE = "video/";

    @Override
    public boolean supports(Class<?> clazz) {
        return NewVideoForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;

        NewVideoForm newVideoForm = (NewVideoForm) target;

        if (newVideoForm.videos.isEmpty()) return;

        newVideoForm.videos.forEach(video -> {
            final String contentType = Objects.requireNonNull(video.getContentType());

            if (!contentType.contains(VIDEO_CONTENT_TYPE)) {
                errors.rejectValue("videos", null, "File Content-Type(%s) not supported".formatted(contentType));
            }
        });
    }
}
