package org.bbtracker.server;

import org.joda.time.Instant;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.kickmyb.server.ServerApplication;
import org.kickmyb.server.account.BadCredentialsException;
import org.kickmyb.server.account.MUser;
import org.kickmyb.server.account.ServiceAccount;
import org.kickmyb.server.task.MTask;
import org.kickmyb.server.task.ServiceTask;
import org.kickmyb.transfer.AddTaskRequest;
import org.kickmyb.transfer.HomeItemPhotoResponse;
import org.kickmyb.transfer.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

// TODO pour celui ci on aimerait pouvoir mocker l'utilisateur pour ne pas avoir à le créer

// https://reflectoring.io/spring-boot-mock/#:~:text=This%20is%20easily%20done%20by,our%20controller%20can%20use%20it.

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
		classes = ServerApplication.class)
@TestPropertySource(
		locations = "classpath:application-integrationtest.properties")
class ServiceTaskTests {

	@Autowired
	private ServiceAccount serviceAccount;

	@Autowired
	private ServiceTask serviceTask;


	@Test
	void testNoDuplicateTasks() {
		// TODO

	}


	@Test
	void testDeletion() throws ServiceTask.Empty, ServiceTask.TooShort, ServiceTask.Existing, ServiceAccount.UsernameTooShort, ServiceAccount.PasswordTooShort, ServiceAccount.UsernameAlreadyTaken, BadCredentialsException {
		MUser testUser = new MUser();
		testUser.username = "tester";
		testUser.id = 2L;

		SignupRequest req = new SignupRequest();
		req.username = "test";
		req.password = "test";
		serviceAccount.signup(req);

		AddTaskRequest taskRequest = new AddTaskRequest();
		taskRequest.name = "task1";
		taskRequest.deadline = LocalDateTime.now().toDate();
		serviceTask.addOne(taskRequest,serviceTask.userFromUsername("test"));

		AddTaskRequest taskRequest2 = new AddTaskRequest();
		taskRequest2.name = "task2";
		taskRequest2.deadline = LocalDateTime.now().toDate();
		serviceTask.addOne(taskRequest2,serviceTask.userFromUsername("test"));

		Assertions.assertEquals(2,serviceTask.homePhoto( serviceTask.userFromUsername("test").id).size());



		serviceTask.deleteTask(2, serviceTask.userFromUsername("test"));
		//serviceTask.userFromUsername("test");

		Assertions.assertEquals(1, serviceTask.homePhoto( serviceTask.userFromUsername("test").id).size());


	}

}
