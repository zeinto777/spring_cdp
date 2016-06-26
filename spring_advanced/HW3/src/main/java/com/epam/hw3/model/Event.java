package com.epam.hw3.model;

import java.util.Date;

/**
 * Created by Andrii_Pinchuk on 11/17/2015.
 */
public interface  Event extends Item {

    String getTitle();

    void setTitle(String title);

    Date getDate();

    void setDate(Date date);

    int getTicketPrice();

    void setTicketPrice(int price);


}
