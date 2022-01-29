package com.loki.caninebookmongo.service.impl;

import com.loki.caninebookmongo.data.entity.User;
import com.loki.caninebookmongo.data.repository.UserRepository;
import com.loki.caninebookmongo.security.config.PasswordConfig;
import com.loki.caninebookmongo.service.UserRegistrationService;
import com.loki.caninebookmongo.service.UserService;
import com.loki.caninebookmongo.service.exceptions.UserAlreadyExistsException;
import com.loki.caninebookmongo.web.dto.UserDTO;
import com.loki.caninebookmongo.web.error.FormMustContainPhoneOrEmailException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserRegistrationServiceImplTest {

    @Mock
    public UserService userService;

    @Mock
    public UserRepository userRepository;


    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    UserRegistrationService userRegistrationService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    @BeforeEach
    public void setUp() {
        userRegistrationService = new UserRegistrationServiceImpl(userRepository, userService, passwordEncoder);
    }

    @Test
    public void shouldContainEitherPhoneOrEmailOrBoth() {
        UserDTO userDTO1 = new UserDTO("Test",
                "Last",
                "test",
                "test",
                null,
                null);


        //When both email and phone are null
        Assertions.assertThatThrownBy(() -> userRegistrationService.registerUser(userDTO1))
                .isInstanceOf(FormMustContainPhoneOrEmailException.class)
                .hasMessage("Phone or email required");

        //when only email is empty
        userDTO1.setEmail("");
        userDTO1.setPhoneNumber(null);
        Assertions.assertThatThrownBy(() -> userRegistrationService.registerUser(userDTO1))
                .isInstanceOf(FormMustContainPhoneOrEmailException.class)
                .hasMessage("Phone or email required");

        //when phoneNumber is empty
        userDTO1.setEmail(null);
        userDTO1.setPhoneNumber("");
        Assertions.assertThatThrownBy(() -> userRegistrationService.registerUser(userDTO1))
                .isInstanceOf(FormMustContainPhoneOrEmailException.class)
                .hasMessage("Phone or email required");

        //When both are empty
        userDTO1.setEmail("");
        userDTO1.setPhoneNumber("");
        Assertions.assertThatThrownBy(() -> userRegistrationService.registerUser(userDTO1))
                .isInstanceOf(FormMustContainPhoneOrEmailException.class)
                .hasMessage("Phone or email required");

        //When both are present
        userDTO1.setEmail("test@test.com");
        userDTO1.setPhoneNumber("+91 12122");
        Assertions.assertThatNoException().isThrownBy( () -> userRegistrationService.registerUser(userDTO1));

        //When email is present
        userDTO1.setEmail("test@test.com");
        userDTO1.setPhoneNumber(null);
        Assertions.assertThatNoException().isThrownBy( () -> userRegistrationService.registerUser(userDTO1));


        //When phone is present
        userDTO1.setEmail("");
        userDTO1.setPhoneNumber("+9121212");
        Assertions.assertThatNoException().isThrownBy( () -> userRegistrationService.registerUser(userDTO1));


    }


    @Test
    @DisplayName("Only one user can exist with a username, email and phone number")
    public void shouldAllowOnlyOneUserPerEmailPhoneAndUserName() {
        UserDTO userDTO = new UserDTO("Test",
                "Last",
                "test",
                "test",
                "test@test.com",
                "+9121212");


        Mockito.when(userService.userExistsByUserName("test")).thenReturn(true);

        //user already exists with username test
        Assertions.assertThatThrownBy(() -> userRegistrationService.registerUser(userDTO))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("user already exists with username: test")
                .hasFieldOrPropertyWithValue("field", "userName");



        //user already exists with email test@test.com
        UserDTO userDTO2 = new UserDTO();
        BeanUtils.copyProperties(userDTO, userDTO2);
        userDTO2.setUserName("random");
        Mockito.when(userService.userExistsByEmail("test@test.com")).thenReturn(true);
        Assertions.assertThatThrownBy(() -> userRegistrationService.registerUser(userDTO2))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("user already exists with email: test@test.com")
                .hasFieldOrPropertyWithValue("field", "email");


        //user already exists with phonenumber +9121212
        UserDTO userDTO3 = new UserDTO();
        BeanUtils.copyProperties(userDTO, userDTO3);
        userDTO3.setUserName("random");
        userDTO3.setEmail("random@test.com");
        Mockito.when(userService.userExistsByPhoneNumber("+9121212")).thenReturn(true);
        Assertions.assertThatThrownBy(() -> userRegistrationService.registerUser(userDTO3))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("user already exists with phoneNumber: +9121212")
                .hasFieldOrPropertyWithValue("field", "phoneNumber");


        //no user exists with username random
        userDTO.setUserName("random");
        //no user exists with email random@test.com
        userDTO.setEmail("random@test.com");
        //no user exists with phonenumber +9121212222
        userDTO.setPhoneNumber("+9121212222");
        Assertions.assertThatNoException().isThrownBy(() -> userRegistrationService.registerUser(userDTO));

    }

    @Test
    public void saveUser() {
        UserDTO userDTO1 = new UserDTO("Test",
                "Last",
                "test",
                "test",
                "test@test.com",
                null);

        User expectedUser = new User(
                "asasas",
                "Test",
                "Last",
                "test",
                "test",
                "test@test.com",
                null);

        userRegistrationService.registerUser(userDTO1);
        Mockito.verify(userRepository, Mockito.times(1))
                .save(userArgumentCaptor.capture());

        User insertedUser = userArgumentCaptor.getValue();

        Assertions.assertThat(insertedUser)
                .isNotNull();

        Assertions.assertThat(insertedUser)
                .usingRecursiveComparison()
                .comparingOnlyFields("firstName", "lastName", "userName", "email", "phoneNumber")
                .isEqualTo(expectedUser);

    }

}