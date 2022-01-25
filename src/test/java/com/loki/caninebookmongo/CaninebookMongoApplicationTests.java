package com.loki.caninebookmongo;

import com.loki.caninebookmongo.data.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CaninebookMongoApplicationTests {

	private static String URI = "http://localhost:";

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void throwsErrorWhenNullIsPassedForName() {

		User user = new User();
		user.setUserName("lkrjt");
		user.setLastName("singh");
		user.setFirstName("lokender");
		Assertions.assertEquals(restTemplate
				.postForEntity(URI + port + "/add-user",
						user,
						User.class
						).getBody().getUserName(), user.getUserName());
	}

}
