package com.epam.hw2.controller;

import com.epam.hw2.constants.Constants;
import com.epam.hw2.facade.BookingFacade;
import com.epam.hw2.model.wrapper.Users;
import com.epam.hw2.model.User;
import com.epam.hw2.model.impl.UserImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

/**
 * Created by Andrii_Pinchuk on 12/9/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    private UserController userController = new UserController();
    private Date date = new Date("Nov 18, 2015");
    @Mock
    BookingFacade bookingFacade;

    @Before
    public void setup() {
        userController.setBookingFacade(bookingFacade);
    }

    @Test
    public void testGetUserById() {
        ModelMap model = new ModelMap();
        User expectedUser = new UserImpl(4187, "Andrii Pinchuk", "Andrii_Pinchuk@epam.com");
        when(bookingFacade.getUserById(anyLong())).thenReturn(expectedUser);

        userController.getUserById(anyLong(), model);

        assertEquals(expectedUser, model.get(Constants.USER));
    }

    @Test
    public void testGetUserByEmail() {
        ModelMap model = new ModelMap();
        User expectedUser = new UserImpl(4186, "Andrii Pinchuk", "Test@epam.com");

        when(bookingFacade.getUserByEmail(anyString())).thenReturn(expectedUser);

        userController.getUserByEmail(anyString(), model);

        assertEquals(expectedUser, model.get(Constants.USER));
    }

    @Test
    public void testGetUserByName() {
        ModelMap model = new ModelMap();
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new UserImpl(4186, "Andrii Pinchuk_Test", "Andrii_Pinchuk@epam.com"));
        expectedUsers.add(new UserImpl(4187, "Andrii Pinchuk_Test", "Andrii_Pinchuk@epam.com"));

        when(bookingFacade.getUsersByName(anyString(), anyInt(), anyInt())).thenReturn(expectedUsers);


        userController.getUsersByName(anyString(), anyInt(), anyInt(), model);

        Users users = (Users) model.get(Constants.USERS);
        assertArrayEquals(expectedUsers.toArray(), (users.getUsers()).toArray());
    }


    @Test
    public void testDeleteUserExpectedDeleteUSerFromDB() {
        ModelMap model = new ModelMap();
        User user = new UserImpl(4186, "Andrii Pinchuk", "Andrii_Pinchuk@epam.com");
        when(bookingFacade.deleteUser(anyLong())).thenReturn(true);

        userController.deleteUser(anyLong(), model);

        assertTrue((Boolean) model.get(Constants.USER));
    }


    @Test
    public void testUpdateUserExpectedChangeUserInDB() {
        ModelMap model = new ModelMap();
        User expectedUser = new UserImpl(4186, "Andrii Pinchuk", "Andrii_Pinchuk@epam.com");
        when(bookingFacade.updateUser(any(User.class))).thenReturn(expectedUser);

        userController.updateUser(anyLong(), "Andrii Pinchuk", "Andrii_Pinchuk@epam.com", model);

        assertEquals(expectedUser, model.get(Constants.USER));
    }

    @Test
    public void testCreateUser() {
        ModelMap model = new ModelMap();
        User expectedUser = new UserImpl(4186, "Andrii Pinchuk", "Andrii_Pinchuk@epam.com");
        when(bookingFacade.createUser(any(User.class))).thenReturn(expectedUser);

        userController.createUser(anyLong(), "Andrii Pinchuk", "Andrii_Pinchuk@epam.com", model);

        assertEquals(expectedUser, model.get(Constants.USER));
    }

}
