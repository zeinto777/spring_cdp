package com.epam.hw3.model.impl;

import com.epam.hw3.model.Ticket;

import javax.xml.bind.annotation.*;

/**
 * Created by Andrii_Pinchuk on 11/17/2015.
 */
@XmlRootElement(name = "ticket")
//@XmlAccessorType(XmlAccessType.FIELD)
public class TicketImpl implements Ticket {
   // @XmlAttribute(name = "id")
    private long id;
   // @XmlAttribute(name = "eventId")
    private long eventId;
   // @XmlAttribute(name = "userId")
    private long userId;
  //  @XmlAttribute(name = "category")
    private Category category;
  //  @XmlAttribute(name = "place")
    private int place;

    public TicketImpl() {
    }

    public TicketImpl(long id, long eventId, long userId, Category category, int place) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.category = category;
        this.place = place;
    }

    public long getId() {
        return id;
    }


    @XmlElement
    public void setId(long id) {
        this.id = id;
    }

    public long getEventId() {
        return eventId;
    }

    @XmlElement
    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getUserId() {
        return userId;
    }

    @XmlElement
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Category getCategory() {
        return category;
    }

    @XmlElement
    public void setCategory(Category category) {
        this.category = category;
    }

    public int getPlace() {
        return place;
    }

    @XmlElement
    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketImpl ticket = (TicketImpl) o;

        if (id != ticket.id) return false;
        if (eventId != ticket.eventId) return false;
        if (userId != ticket.userId) return false;
        if (place != ticket.place) return false;
        return category == ticket.category;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (eventId ^ (eventId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + place;
        return result;
    }

    @Override
    public String toString() {
        return "\r\nTicketImpl{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", userId=" + userId +
                ", category=" + category +
                ", place=" + place +
                '}';
    }
}
