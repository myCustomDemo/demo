package com.github.demo.controller;

import com.github.demo.dao.UserDao;
import com.github.demo.domain.User;
import org.mockito.Mock;
import org.springframework.ui.Model;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

public class UserControllerTest {

    @Mock
    private UserDao userDao;

    private UsersController victim;


    @BeforeClass
    public void setupScenario() {
        initMocks(this);

        victim = new UsersController(userDao);
    }

    @Test
    public void shouldRetrieveAListOfUsers() {
        List<User> users = newArrayList(
                new User(1, "ABC"),
                new User(2, "XPT")
        );

        when(userDao.findAll()).thenReturn(users);

        Model model = mock(Model.class);

        String result = victim.index(model);

        verify(model).addAttribute(eq("users"), eq(users));

        assertEquals(result, "index");
    }

}
