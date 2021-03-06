package com.epam.hw1.model.wrapper;

import com.epam.hw1.model.Event;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
/**
 * Created by Andrii_Pinchuk on 12/10/2015.
 */
@XmlRootElement(name = "events")
public class Events {
    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    @XmlAnyElement
    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
