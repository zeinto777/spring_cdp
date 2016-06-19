package com.epam.hw2.service.impl;

import com.epam.hw2.dao.UserAccountDAO;
import com.epam.hw2.model.UserAccount;
import com.epam.hw2.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Andrii_Pinchuk on 11/23/2015.
 */
public class UserAccountServiceImpl implements UserAccountService {
    @Autowired
    private UserAccountDAO userAccountDAO;

    @Override
    public UserAccount getUserAccountByUserId(long id) {
        return userAccountDAO.getUserAccountByUserId(id);
    }

    @Override
    public UserAccount updateUserAccount(UserAccount currentUserAccount) {
        return userAccountDAO.updateUserAccount(currentUserAccount);
    }

    public UserAccount createUserAccount(UserAccount currentUserAccount) {
        return userAccountDAO.createUserAccount(currentUserAccount);
    }

    @Override
    public boolean deleteUserAccount(long userAccountId) {
        return userAccountDAO.deleteUserAccount(userAccountId);
    }
}
