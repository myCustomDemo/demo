package com.github.demo.dao;

import com.datastax.driver.core.Row;
import com.github.demo.domain.User;
import com.github.demo.util.AbstractCassandraTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class UserDaoImplTest extends AbstractCassandraTest {

    private UserDao userDao;

    @BeforeClass
    public void setUp() throws Exception {
        userDao = new UserDaoImpl(cassandra.session) {

        };
    }

    @Test
    public void shouldReturnStoredPersons() throws Exception {
        //given
        User john = new User(123, "John");
        User anna = new User(567, "Anna");

        //when
        List<User> userList = userDao.findAll();

        //then
        assertThat(userList, hasItem(john));
        assertThat(userList, hasItem(anna));
    }

    @Test
    public void shouldInsertNewPerson() throws Exception {
        //given
        int userId = 988;
        String userData = "Joanne";
        User newUser = new User(userId, userData);

        //when
        userDao.insert(newUser);

        // then
        Row personRow = fetchPersonRowById(userId);

        assertThat(personRow.getInt("id"), equalTo(userId));
        assertThat(userDao.convertByteBufferToString(personRow.getBytes("data")), equalTo(userData));
    }

}