package com.loki.caninebookmongo.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loki.caninebookmongo.data.repository.UserRepository;
import com.loki.caninebookmongo.web.dto.UserDTO;
import com.loki.caninebookmongo.web.dto.UserNameAndPasswordAuthenticationRequestDTO;
import com.loki.caninebookmongo.web.dto.ValidationFailedResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

@AutoConfigureMockMvc
@SpringBootTest
class UserIAMControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void addUser() throws Exception{
        UserDTO userDTO1 = new UserDTO("test",
                "Last",
                "test",
                "test",
                "test@test.com",
                "+91222222");

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/register-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @AfterEach
    public void deleteUser() {
        userRepository.deleteByUserName("test");
    }

    @Test
    public void happyPathTesting() throws Exception {

        UserNameAndPasswordAuthenticationRequestDTO userNameAndPasswordAuthenticationRequestDTO =
                new UserNameAndPasswordAuthenticationRequestDTO();
        userNameAndPasswordAuthenticationRequestDTO.setUsername("test");
        userNameAndPasswordAuthenticationRequestDTO.setPassword("test");

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/iam/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userNameAndPasswordAuthenticationRequestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String token = mvcResult.getResponse().getHeader("Authorization");
        System.out.println(token);
        Assertions.assertThat(token)
                .isNotNull();

        Assertions.assertThat(token)
                .startsWith("Bearer");

    }

    @Test
    public void shouldNotLoginIfUserDoesNotExist() throws Exception {
        UserNameAndPasswordAuthenticationRequestDTO userNameAndPasswordAuthenticationRequestDTO =
                new UserNameAndPasswordAuthenticationRequestDTO();
        userNameAndPasswordAuthenticationRequestDTO.setUsername("random");
        userNameAndPasswordAuthenticationRequestDTO.setPassword("random");

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/iam/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userNameAndPasswordAuthenticationRequestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        Map<String, String> error = new HashMap<>();
        error.put("authentication", "No such user exists!");
        ValidationFailedResponse validationFailedResponse = new ValidationFailedResponse(error);

        Assertions.assertThat(response)
                .isEqualTo(objectMapper.writeValueAsString(validationFailedResponse));

    }

    @Test
    public void shouldNotLoginIfPasswordIsIncorrect() throws Exception {
        UserNameAndPasswordAuthenticationRequestDTO userNameAndPasswordAuthenticationRequestDTO =
                new UserNameAndPasswordAuthenticationRequestDTO();
        userNameAndPasswordAuthenticationRequestDTO.setUsername("test");
        userNameAndPasswordAuthenticationRequestDTO.setPassword("random");

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/iam/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userNameAndPasswordAuthenticationRequestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        Map<String, String> error = new HashMap<>();
        error.put("authentication", "Incorrect password!");
        ValidationFailedResponse validationFailedResponse = new ValidationFailedResponse(error);

        Assertions.assertThat(response)
                .isEqualTo(objectMapper.writeValueAsString(validationFailedResponse));

    }
}