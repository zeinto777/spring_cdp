package com.epam.hw1.service;

import com.epam.hw1.model.UserAccount;

/**
 * Created by Andrii_Pinchuk on 11/23/2015.
 */
public interface UserAccountService {
    UserAccount getUserAccountByUserId(long id);

    UserAccount updateUserAccount(UserAccount userAccount);

    UserAccount createUserAccount(UserAccount userAccount);

    boolean deleteUserAccount(long userAccountId);
}


