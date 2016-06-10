package com.epam.hw1.dao.impl;

import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.UserImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Andrii_Pinchuk on 11/19/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserDAOImplTest {
    private UserDAOImpl userDAO = new UserDAOImpl();
    private Date date = new Date("Nov 18, 2015");

    @Mock
    JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        userDAO.setJdbcTemplate(jdbcTemplate);
    }

    @Test
    public void testGetUserById() {
        User expectedUser = new UserImpl(4187, "Andrii Pinchuk", "Andrii_Pinchuk@epam.com");
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(UserDAOImpl.UserMapper.class))).thenReturn(expectedUser);

        User resultUser = userDAO.getUserById(4187);

        assertEquals(expectedUser, resultUser);
    }


    @Test
    public void testGetUserByEmail() {
        User expectedUser = new UserImpl(4186, "Andrii Pinchuk", "Test@epam.com");
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(UserDAOImpl.UserMapper.class))).thenReturn(expectedUser);

        User resultUser = userDAO.getUserByEmail(expectedUser.getEmail());

        assertEquals(expectedUser, resultUser);
    }


    @Test
    public void testGetUserByName() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new UserImpl(4186, "Andrii Pinchuk_Test", "Andrii_Pinchuk@epam.com"));
        expectedUsers.add(new UserImpl(4187, "Andrii Pinchuk_Test", "Andrii_Pinchuk@epam.com"));
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(UserDAOImpl.UserMapper.class))).thenReturn(expectedUsers);

        List<User> resultUsers = userDAO.getUsersByName("Andrii Pinchuk_Test", 2, 0);

        assertArrayEquals(expectedUsers.toArray(), resultUsers.toArray());
    }

    @Test
    public void testDeleteUserExpectedDeleteUSerFromDB() {
        User user = new UserImpl(4186, "Andrii Pinchuk", "Andrii_Pinchuk@epam.com");
        when(jdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);

        boolean result = userDAO.deleteUser(user.getId());

        assertTrue(result);
    }

    @Test
    public void testUpdateUserExpectedChangeUserInDB() {
        User expectedUser = new UserImpl(4186, "Andrii Pinchuk", "Andrii_Pinchuk@epam.com");
        when(jdbcTemplate.update(anyString(), any(Object[].class), Matchers.<int[]>any())).thenReturn(1);

        User resultUser = userDAO.updateUser(expectedUser);

        assertEquals(expectedUser, resultUser);
    }
}
