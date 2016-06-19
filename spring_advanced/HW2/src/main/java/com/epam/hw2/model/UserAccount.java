package com.epam.hw2.model;

/**
 * Created by Andrii_Pinchuk on 11/23/2015.
 */
public interface UserAccount extends Item {

    int getPrepaidMoney();

    void setPrepaidMoney(int prepaidMoney);

    long getUserId();

    void setUserId(long id);
}
