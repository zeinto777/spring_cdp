package com.epam.hw2.controller;

import com.epam.hw2.constants.Constants;
import com.epam.hw2.facade.BookingFacade;
import com.epam.hw2.model.wrapper.Users;
import com.epam.hw2.model.User;
import com.epam.hw2.model.impl.UserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Andrii_Pinchuk on 12/5/2015.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private BookingFacade bookingFacade;

    public void setBookingFacade(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public String getUserById(@RequestParam("id") Long id, ModelMap model) {
        LOG.debug("Get user by ID start. ID = " + id);
        User user = bookingFacade.getUserById(id);
        model.put(Constants.USER, user);
        LOG.debug("Get user by ID end. User id = " + user.getId());
        return Constants.USER;
    }

    @RequestMapping(value = "/get", params = {"email"}, method = RequestMethod.GET)
    public String getUserByEmail(@RequestParam("email") String email, ModelMap model) {
        LOG.debug("Get user by email  start. Email = " + email);
        User user = bookingFacade.getUserByEmail(email);
        model.put(Constants.USER, user);
        LOG.info("Get user by email end. User = " + user);
        return Constants.USER;
    }

    @RequestMapping(value = "/{name}/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public String getUsersByName(@PathVariable(value = "name") String name, @PathVariable("pageNum") int pageNum,
                                 @PathVariable("pageSize") int pageSize, ModelMap model) {
        LOG.debug("getUserById()- name, pageSize, pageNum {}", Arrays.asList(name, pageSize, pageNum));
        List<User> users = bookingFacade.getUsersByName(name, pageSize, pageNum);
        Users userWrapper = new Users();
        userWrapper.setUsers(users);
        LOG.debug("getUserById()- end" + users);
        model.put("users", userWrapper);
        model.put("user_name", name);
        return Constants.USER;
    }

    @RequestMapping(value = "/create/{id}/{name}/{email}", method = RequestMethod.GET)
    public String createUser(@PathVariable(value = "id") Long id, @PathVariable("name") String name,
                             @PathVariable("email") String email, ModelMap model) {
        User user = new UserImpl(id, name, email);
        LOG.debug(String.format("Create user start. User - %s", user));
        User createdUser = bookingFacade.createUser(user);
        LOG.debug("Create user end. Result = " + createdUser);
        model.put(Constants.USER, createdUser);
        return Constants.USER;
    }


    @RequestMapping(value = "/delete", params = {"id"}, method = RequestMethod.GET)
    public String deleteUser(@RequestParam("id") Long id, ModelMap model) {
        LOG.debug(String.format("Delete user start. User_id = %d", id));
        boolean result = bookingFacade.deleteUser(id);
        model.put(Constants.USER, result);
        LOG.debug("Delete user end. Result = " + result);
        return Constants.USER;
    }

    @RequestMapping(value = "/update/{id}/{name}/{email}", method = RequestMethod.GET)
    public String updateUser(@PathVariable(value = "id") Long id, @PathVariable("name") String name,
                             @PathVariable("email") String email, ModelMap model) {
        User user = new UserImpl(id, name, email);
        LOG.debug(String.format("Update user start. User =  %s", user));
        User updatedUser = bookingFacade.updateUser(user);
        model.put(Constants.USER, updatedUser);
        LOG.debug("Update user end. Result = " + user);
        model.put("message", "Success update user");
        return Constants.USER;
    }
}
