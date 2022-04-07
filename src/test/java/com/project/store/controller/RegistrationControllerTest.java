package com.project.store.controller;

import com.project.store.controller.dto.RegistrationRequest;
import com.project.store.service.RegistrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    @Test
    void shouldRegisterNewUser() throws Exception {
        mockMvc.perform(post("/registration")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"firstName\": \"Oleg\", " +
                            "\"lastName\": \"Oleg\", " +
                            "\"password\": \"password\"," +
                            "\"email\":\"email@mail.ru\"}, " +
                            "\"phone\": \"375292999292\" }"))
                .andExpect(status().isOk());

        verify(registrationService).register(any(RegistrationRequest.class));
    }

    @Test
    void shouldConfirmUser() throws Exception {
        mockMvc.perform(
                get("/registration/confirm")
                .param("token", "token")
        ).andExpect(status().isOk());

        verify(registrationService).confirmToken("token");
    }
}