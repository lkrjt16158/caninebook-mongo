package com.loki.caninebookmongo.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loki.caninebookmongo.data.entity.User;
import com.loki.caninebookmongo.data.repository.UserRepository;
import com.loki.caninebookmongo.service.UserRegistrationService;
import com.loki.caninebookmongo.service.UserService;
import com.loki.caninebookmongo.web.dto.UserDTO;
import com.loki.caninebookmongo.web.dto.ValidationFailedResponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;


@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;


    @Autowired
    UserRepository userRepository;

    @Test
    public void happyPath() throws Exception{
        UserDTO userDTO1 = new UserDTO("test",
                "Last",
                "test",
                "test",
                "test@test.com",
                "+91222222");

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/register-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        System.out.println(response);

        User user = new User(
                "adsadsa",
                "test",
                "Last",
                "test",
                "test",
                "test@test.com",
                "+91222222");

        Assertions.assertThat(userDTO1)
                .usingRecursiveComparison()
                .ignoringFields("userId")
                .isEqualTo(user);

    }

    @Test
    public void shouldReturnErrorWhenMandatoryFieldsAreMissing() throws Exception {
        UserDTO userDTO1 = new UserDTO(null,
                "Last",
                null,
                "test",
                "test@test.com",
                "+91222222");

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/register-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        System.out.println(response);

        Map<String, String > errors = new HashMap<>();
        errors.put("userName", "UserName should not be null or empty");
        errors.put("firstName", "First name should not be null or empty");
        ValidationFailedResponseDTO validationFailedResponseDTO = new ValidationFailedResponseDTO(errors);
        String expectedResponse = objectMapper.writeValueAsString(validationFailedResponseDTO);

        Assertions.assertThat(response).isEqualTo(expectedResponse);

    }

    @Test
    public void shouldReturnErrorIfPhoneNumberIsInvalid() throws Exception {
        UserDTO userDTO1 = new UserDTO("test",
                "Last",
                "test",
                "test",
                "test@test.com",
                "sdad");

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/register-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        System.out.println(response);

        Map<String, String > errors = new HashMap<>();
        errors.put("phoneNumber", "Invalid phone number");
        ValidationFailedResponseDTO validationFailedResponseDTO = new ValidationFailedResponseDTO(errors);
        String expectedResponse = objectMapper.writeValueAsString(validationFailedResponseDTO);

        Assertions.assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void shouldReturnErrorIfEmailIsInvalid() throws Exception {
        UserDTO userDTO1 = new UserDTO("test",
                "Last",
                "test",
                "test",
                "dadad",
                "+91232333");

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/register-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        System.out.println(response);

        Map<String, String > errors = new HashMap<>();
        errors.put("email", "Invalid email");
        ValidationFailedResponseDTO validationFailedResponseDTO = new ValidationFailedResponseDTO(errors);
        String expectedResponse = objectMapper.writeValueAsString(validationFailedResponseDTO);
        Assertions.assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void shouldReturnErrorIfBothEmailAndPhoneNumberAreMissing() throws Exception {
        UserDTO userDTO1 = new UserDTO("test",
                "Last",
                "test",
                "test",
                null,
                null);

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/register-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        System.out.println(response);

        Map<String, String > errors = new HashMap<>();
        errors.put("email", "Phone or email required");
        errors.put("phoneNumber", "Phone or email required");
        ValidationFailedResponseDTO validationFailedResponseDTO = new ValidationFailedResponseDTO(errors);
        String expectedResponse = objectMapper.writeValueAsString(validationFailedResponseDTO);
        Assertions.assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void shouldReturnErrorIfUserAlreadyExist() throws Exception {
        UserDTO userDTO1 = new UserDTO("test",
                "Last",
                "test",
                "test",
                "test@test.com",
                "+9121212");

        ObjectMapper objectMapper = new ObjectMapper();

        userRepository.deleteAll();
        //add user
        mockMvc.perform(MockMvcRequestBuilders.post("/register-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/register-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(409))
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        System.out.println(response);

        Map<String, String > errors = new HashMap<>();
        errors.put("userName", "user already exists with username: test");
        ValidationFailedResponseDTO validationFailedResponseDTO = new ValidationFailedResponseDTO(errors);
        String expectedResponse = objectMapper.writeValueAsString(validationFailedResponseDTO);
        Assertions.assertThat(response).isEqualTo(expectedResponse);
    }
}