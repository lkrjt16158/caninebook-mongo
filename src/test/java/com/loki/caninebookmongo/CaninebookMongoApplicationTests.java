package com.loki.caninebookmongo;

import com.loki.caninebookmongo.web.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


class CaninebookMongoApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testRegistration() {

        UserDTO userDTO1 = new UserDTO("Test",
                "Last",
                "test",
                "test",
                "test@test.com",
                null);


    }
}
