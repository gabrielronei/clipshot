package br.com.fiap.clipshot.core.video;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class NewVideoFormValidatorTest {

    private NewVideoFormValidator newVideoFormValidator;

    @BeforeEach
    void setUp() {
        this.newVideoFormValidator = new NewVideoFormValidator();
    }

    @Test
    void shouldReturnAnErrorWhenItAlreadyExists() {
        NewVideoForm newVideoForm = new NewVideoForm(List.of());

        BindingResult result = new BeanPropertyBindingResult(newVideoForm, "newVideoForm");

        this.newVideoFormValidator.validate(newVideoForm, result);
        assertThat(result.getErrorCount()).isEqualTo(0);

        result.addError(new ObjectError("newVideoForm", "error"));

        this.newVideoFormValidator.validate(newVideoForm, result);

        assertThat(result.getErrorCount()).isEqualTo(1);
    }

    @Test
    void shouldReturnAnErrorWhenContentTypeIsNotSupported() {
        NewVideoForm newVideoForm = new NewVideoForm(List.of(getMultipartFile("application/octet-stream"), getMultipartFile("video/mp4")));

        BindingResult result = new BeanPropertyBindingResult(newVideoForm, "newVideoForm");
        this.newVideoFormValidator.validate(newVideoForm, result);

        assertThat(result.getErrorCount()).isEqualTo(1);
        assertThat(result.getAllErrors().getFirst().getDefaultMessage()).isEqualTo("File Content-Type(application/octet-stream) not supported");
    }

    @Test
    void shouldReturnNoErrorWhenContentTypeIsSupported() {
        NewVideoForm newVideoForm = new NewVideoForm(List.of(getMultipartFile("video/mp4")));

        BindingResult result = new BeanPropertyBindingResult(newVideoForm, "newVideoForm");
        this.newVideoFormValidator.validate(newVideoForm, result);

        assertThat(result.getErrorCount()).isEqualTo(0);
    }

    private MultipartFile getMultipartFile(String contentType) {
        return new MockMultipartFile(UUID.randomUUID().toString(), "xpto", contentType, new byte[0]);
    }
}