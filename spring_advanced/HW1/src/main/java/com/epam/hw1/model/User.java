package com.epam.hw1.model;

/**
 * Created by Andrii_Pinchuk on 11/17/2015.
 */
public interface User extends Item {

    String getName();

    void setName(String name);

    String getEmail();

    void setEmail(String email);
}
