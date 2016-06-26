package com.epam.hw3.model.impl;

import com.epam.hw3.model.Event;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by Andrii_Pinchuk on 11/17/2015.
 */
@XmlRootElement(name = "event")
public class EventImpl implements Event {
    private long id;
    private String title;
    private Date date;
    private int ticketPrice;

    public EventImpl() {
    }

    public EventImpl(long id, String title, Date date, int ticketPrice) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.ticketPrice = ticketPrice;
    }

    public long getId() {
        return id;
    }

    @XmlElement
    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    @XmlElement
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int getTicketPrice() {
        return ticketPrice;
    }

    @XmlElement
    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventImpl event = (EventImpl) o;

        if (id != event.id) return false;
        if (title != null ? !title.equals(event.title) : event.title != null) return false;
        return !(date != null ? !date.equals(event.date) : event.date != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EventImpl{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", ticketPrice=" + ticketPrice +
                '}';
    }
}
