package com.epam.hw1.model.wrapper;

import com.epam.hw1.model.User;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Andrii_Pinchuk on 12/10/2015.
 */
@XmlRootElement(name = "users")
public class Users {
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    @XmlAnyElement
    public void setUsers(List<User> users) {
        this.users = users;
    }
}
