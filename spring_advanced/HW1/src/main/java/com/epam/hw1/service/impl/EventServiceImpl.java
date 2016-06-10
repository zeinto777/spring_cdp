package com.epam.hw1.service.impl;

import com.epam.hw1.model.Event;
import com.epam.hw1.dao.EventDAO;
import com.epam.hw1.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by Andrii_Pinchuk on 11/17/2015.
 */
public class EventServiceImpl implements EventService {
    @Autowired
    private EventDAO eventDAO;

    public Event getEventById(long id) {
        return eventDAO.getEventById(id);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventDAO.getEventsByTitle(title, pageSize, pageNum);
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return eventDAO.getEventsForDay(day, pageSize, pageNum);
    }

    public Event createEvent(Event event) {
        return eventDAO.createEvent(event);
    }

    public Event updateEvent(Event event) {
        return eventDAO.updateEvent(event);
    }

    public boolean deleteEvent(long eventId) {
        return eventDAO.deleteEvent(eventId);
    }
}
