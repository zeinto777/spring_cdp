package com.epam.hw1.model;

/**
 * Created by Andrii_Pinchuk on 11/17/2015.
 */
public interface Ticket extends Item {
    enum Category {PREMIUM, BAR, STANDARD}

    long getEventId();

    void setEventId(long eventId);

    long getUserId();

    void setUserId(long userId);

    Category getCategory();

    void setCategory(Category category);

    int getPlace();

    void setPlace(int place);


}
