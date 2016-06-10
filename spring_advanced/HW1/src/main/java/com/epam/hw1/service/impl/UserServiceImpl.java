package com.epam.hw1.service.impl;

import com.epam.hw1.dao.UserDAO;
import com.epam.hw1.model.User;
import com.epam.hw1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Andrii_Pinchuk on 11/17/2015.
 */
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    public User getUserById(long id) {
        return userDAO.getUserById(id);
    }

    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return userDAO.getUsersByName(name, pageSize, pageNum);
    }

    public User createUser(User user) {
        return userDAO.createUser(user);
    }

    public User updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public boolean deleteUser(long userId) {
        return userDAO.deleteUser(userId);
    }

}
