package com.epam.hw2.controller;

import com.epam.hw2.constants.Constants;
import com.epam.hw2.facade.BookingFacade;
import com.epam.hw2.model.UserAccount;
import com.epam.hw2.model.impl.UserAccountImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

/**
 * Created by Andrii_Pinchuk on 12/6/2015.
 */
@Controller
@RequestMapping("/userAccount")
public class UserAccountController {
    private static final Logger LOG = LoggerFactory.getLogger(UserAccountController.class);

    @Autowired
    private BookingFacade bookingFacade;

    public void setBookingFacade(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @RequestMapping(value = "/get", params = {"userId"}, method = RequestMethod.GET)
    public String getUserAccountByUserId(@RequestParam("userId") Long userId, ModelMap model) {
        LOG.debug("getUserAccountByUserId() start. ID = " + userId);
        UserAccount userAccount = bookingFacade.getUserAccountByUserId(userId);
        model.put(Constants.USER_ACCOUNT, userAccount);
        LOG.debug("getUserAccountByUserId() end. Result = " + userAccount);
        return Constants.ACCOUNT;
    }

    @RequestMapping(value = "/create", params = {"id", "money", "userId"}, method = RequestMethod.GET)
    public String createdUserAccount(@RequestParam("id") Long id, @RequestParam("userId") Long userId, @RequestParam("money") int money, ModelMap model) {
        LOG.debug("createUserAccount() start - id, money, userId {}", Arrays.asList(id, money, userId));
        UserAccount userAccount = new UserAccountImpl(id, userId, money);
        UserAccount createdUserAccount = bookingFacade.createUserAccount(userAccount);
        model.put(Constants.USER_ACCOUNT, createdUserAccount);
        LOG.debug("createdUserAccount() end. Result = " + createdUserAccount);
        return Constants.ACCOUNT;
    }

    @RequestMapping(value = "/delete", params = {"id"}, method = RequestMethod.GET)
    public String deleteUserAccount(@RequestParam("id") Long id, ModelMap model) {
        LOG.debug(String.format("deleteUserAccount() UserAccount_id = %d", id));
        boolean result = bookingFacade.deleteUserAccount(id);
        model.put(Constants.USER_ACCOUNT, result);
        LOG.debug("deleteUserAccount() end. Result = " + result);
        return Constants.ACCOUNT;
    }


}
