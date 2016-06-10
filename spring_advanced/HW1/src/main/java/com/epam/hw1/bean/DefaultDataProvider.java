package com.epam.hw1.bean;

import com.epam.hw1.model.Event;
import com.epam.hw1.model.User;

/**
 * Created by Andrii_Pinchuk on 11/24/2015.
 */
public class DefaultDataProvider {
    private Event defaultEvent;
    private User defaultUser;

    public DefaultDataProvider() {
    }

    public DefaultDataProvider(Event defaultEvent, User defaultUser) {
        this.defaultEvent = defaultEvent;
        this.defaultUser = defaultUser;
    }

    public Event getDefaultEvent() {
        return defaultEvent;
    }

    public void setDefaultEvent(Event defaultEvent) {
        this.defaultEvent = defaultEvent;
    }

    public User getDefaultUser() {
        return defaultUser;
    }

    public void setDefaultUser(User defaultUser) {
        this.defaultUser = defaultUser;
    }

    @Override
    public String toString() {
        return "DefaultDataProvider{" +
                "defaultEvent=" + defaultEvent +
                ", defaultUser=" + defaultUser +
                '}';
    }
}
