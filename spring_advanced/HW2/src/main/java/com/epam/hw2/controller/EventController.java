package com.epam.hw2.controller;

import com.epam.hw2.constants.Constants;
import com.epam.hw2.facade.BookingFacade;
import com.epam.hw2.model.Event;
import com.epam.hw2.model.impl.EventImpl;
import com.epam.hw2.model.wrapper.Events;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by Andrii_Pinchuk on 12/5/2015.
 */
@Controller("eventController")
@RequestMapping("/event")
public class EventController {
    private static final Logger LOG = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private BookingFacade bookingFacade;

    public void setBookingFacade(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public String getEventById(@RequestParam("id") Long id, ModelMap model) {
        LOG.debug("Get event by ID start. ID = " + id);
        Event event = bookingFacade.getEventById(id);
        model.put(Constants.EVENT, event);
        LOG.debug("Get event by ID end. Result = " + event);
        return Constants.EVENT;
    }

    @RequestMapping(value = "/{name}/{pageNum}/{pageSize}", method = RequestMethod.GET, headers = "Accept=application/pdf")
    public ModelAndView getEventsByTitlePDF(@PathVariable(value = "name") String name, @PathVariable("pageNum") int pageNum,
                                            @PathVariable("pageSize") int pageSize) {
        LOG.debug("getEventsByTitle() start - name, pageSize, pageNum {}", Arrays.asList(name, pageSize, pageNum));
        List<Event> events = bookingFacade.getEventsByTitle(name, pageSize, pageNum);
        LOG.debug("getEventsByTitle()- end" + events);
        return new ModelAndView("pdfView", "eventPdf", events);
    }


    @RequestMapping(value = "/{name}/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public String getEventsByTitle(@PathVariable(value = "name") String name, @PathVariable("pageNum") int pageNum,
                                   @PathVariable("pageSize") int pageSize, ModelMap model) {
        LOG.debug("getEventsByTitle() start - name, pageSize, pageNum {}", Arrays.asList(name, pageSize, pageNum));
        List<Event> events = bookingFacade.getEventsByTitle(name, pageSize, pageNum);
        Events eventWrapper = new Events();
        eventWrapper.setEvents(events);
        LOG.debug("getEventsByTitle()- end" + events);
        model.put("events", eventWrapper);
        return Constants.EVENT;
    }


    @RequestMapping(value = "/create", params = {"id", "title", "date", "ticketPrice"}, method = RequestMethod.GET)
    public String createEvent(@RequestParam("id") Long id, @RequestParam("title") String title, @RequestParam("date") Date date, @RequestParam("ticketPrice") int ticketPrice, ModelMap model) {
        LOG.debug("getEventsByTitle() start - id, title, date, ticketPrice {}", Arrays.asList(id, title, date, ticketPrice));
        Event event = new EventImpl(id, title, date, ticketPrice);
        Event createdEvent = bookingFacade.createEvent(event);
        model.put(Constants.EVENT, createdEvent);
        LOG.debug("getEventsByTitle() end. Result = " + createdEvent);
        return Constants.EVENT;
    }

    @RequestMapping(value = "/delete", params = {"id"}, method = RequestMethod.GET)
    public String deleteEvent(@RequestParam("id") Long id, ModelMap model) {
        LOG.debug(String.format("deleteEvent() start. Event_id = %d", id));
        boolean result = bookingFacade.deleteEvent(id);
        model.put(Constants.EVENT, result);
        LOG.debug("deleteEvent() end. Result = " + result);
        return Constants.EVENT;
    }

    @RequestMapping(value = "/update", params = {"id", "title", "date", "ticketPrice"}, method = RequestMethod.GET)
    public String updateEvent(@RequestParam("id") Long id, @RequestParam("title") String title, @RequestParam("date") Date date, @RequestParam("ticketPrice") int ticketPrice, ModelMap model) {
        LOG.debug("updateEvent() start - id, title, date, ticketPrice {}", Arrays.asList(id, title, date, ticketPrice));
        Event event = new EventImpl(id, title, date, ticketPrice);
        Event updatedEvent = bookingFacade.updateEvent(event);
        model.put(Constants.EVENT, updatedEvent);
        LOG.debug("updateEvent() end" + updatedEvent);
        return Constants.EVENT;
    }

    @RequestMapping(value = "/forDay", params = {"date", "pageNum", "pageSize"}, method = RequestMethod.GET)
    public String getEventsForDay(@RequestParam("date") Date date, @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, ModelMap model) {
        LOG.debug("getEventsForDay() start - date, pageSize, pageNum {}", Arrays.asList(date, pageSize, pageNum));
        List<Event> events = bookingFacade.getEventsForDay(date, pageSize, pageNum);
        Events eventWrapper = new Events();
        eventWrapper.setEvents(events);
        LOG.debug("getEventsForDay()- end" + events);
        model.put(Constants.EVENTS, eventWrapper);
        return Constants.EVENT;
    }
}
