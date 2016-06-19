package com.epam.hw2.model.impl;

import com.epam.hw2.model.UserAccount;

/**
 * Created by Andrii_Pinchuk on 11/23/2015.
 */
public class UserAccountImpl implements UserAccount {
    private long id;
    private long userId;
    private int prepaidMoney;

    public UserAccountImpl() {
    }

    public UserAccountImpl(long id, long userId, int prepaidMoney) {
        this.id = id;
        this.userId = userId;
        this.prepaidMoney = prepaidMoney;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public int getPrepaidMoney() {
        return prepaidMoney;
    }

    @Override
    public void setPrepaidMoney(int prepaidMoney) {
        this.prepaidMoney = prepaidMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccountImpl that = (UserAccountImpl) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        return prepaidMoney == that.prepaidMoney;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + prepaidMoney;
        return result;
    }

    @Override
    public String toString() {
        return "UserAccountServiceImpl{" +
                "id=" + id +
                ", userId=" + userId +
                ", prepaidMoney=" + prepaidMoney +
                '}';
    }
}
