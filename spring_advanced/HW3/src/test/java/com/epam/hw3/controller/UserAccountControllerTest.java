package com.epam.hw3.controller;

import com.epam.hw3.facade.BookingFacade;
import com.epam.hw3.model.UserAccount;
import com.epam.hw3.model.impl.UserAccountImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;

import java.util.Date;

import static com.epam.hw3.constants.Constants.USER_ACCOUNT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Created by Andrii_Pinchuk on 12/9/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserAccountControllerTest {
    private UserAccountController userAccountController = new UserAccountController();
    private Date date = new Date("Nov 18, 2015");
    @Mock
    BookingFacade bookingFacade;

    @Before
    public void setup() {
        userAccountController.setBookingFacade(bookingFacade);
    }

    @Test
    public void testGetUserAccountByUserId() {
        ModelMap model = new ModelMap();
        UserAccount userAccount = new UserAccountImpl(4186, 41862, 45);

        when(bookingFacade.getUserAccountByUserId(anyLong())).thenReturn(userAccount);

        userAccountController.getUserAccountByUserId(anyLong(), model);
        assertEquals(userAccount, model.get(USER_ACCOUNT));
    }

    @Test
    public void testDeleteUserAccount() {
        ModelMap model = new ModelMap();

        when(bookingFacade.deleteUserAccount(anyLong())).thenReturn(true);

        userAccountController.deleteUserAccount(anyLong(), model);
        assertTrue((Boolean) model.get(USER_ACCOUNT));
    }


    @Test
    public void testCreatedUserAccount() {
        ModelMap model = new ModelMap();
        UserAccount userAccount = new UserAccountImpl(4186, 41862, 45);
        when(bookingFacade.createUserAccount(any(UserAccount.class))).thenReturn(userAccount);

        userAccountController.createdUserAccount(4186L, 4186L, 45, model);
        assertEquals(userAccount, model.get(USER_ACCOUNT));
    }
}
