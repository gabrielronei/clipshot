package br.com.fiap.clipshot.core.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnStatusCreated() throws Exception {
        NewUserForm newUserForm = new NewUserForm("gab", "gab@email.com", "gab");

        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users").content(objectMapper.writeValueAsString(newUserForm))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnBadRequestIfUserHasBeenAlreadyCreated() throws Exception {
        NewUserForm newUserForm = new NewUserForm("gab", "gab@email.com", "gab");

        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users").content(objectMapper.writeValueAsString(newUserForm))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users").content(objectMapper.writeValueAsString(newUserForm))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldReturnBadRequestIfUserHasBeenAlreadyCreated2() throws Exception {
        NewUserForm newUserForm = new NewUserForm("gab", "gab@email.com", "gab");

        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users").content(objectMapper.writeValueAsString(newUserForm))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        UserLoginForm loginForm = new UserLoginForm(newUserForm.getEmail(), newUserForm.getPassword());
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/login").content(objectMapper.writeValueAsString(loginForm))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}