package com.epam.hw2.dao;

import com.epam.hw2.model.UserAccount;

/**
 * Created by Andrii_Pinchuk on 11/23/2015.
 */
public interface UserAccountDAO {

    UserAccount getUserAccountByUserId(long id);

    UserAccount updateUserAccount(UserAccount userAccount);

    UserAccount createUserAccount(UserAccount userAccount);

    boolean deleteUserAccount(long userAccountId);

}
