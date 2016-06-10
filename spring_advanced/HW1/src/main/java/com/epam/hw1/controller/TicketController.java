package com.epam.hw1.controller;

import com.epam.hw1.model.Event;
import com.epam.hw1.model.Ticket;
import com.epam.hw1.model.impl.EventImpl;
import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.TicketImpl;
import com.epam.hw1.model.wrapper.Tickets;
import com.epam.hw1.model.impl.UserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.epam.hw1.constants.Constants.TICKET;

/**
 * Created by Andrii_Pinchuk on 12/5/2015.
 */
@Controller
@RequestMapping("/ticket")
public class TicketController {
    private static final Logger LOG = LoggerFactory.getLogger(TicketController.class);
    @Autowired
    private BookingFacade bookingFacade;

    public void setBookingFacade(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @RequestMapping(value = "/get", params = {"id"}, method = RequestMethod.GET)
    public String getTicketById(@RequestParam("id") Long id, ModelMap model) {
        LOG.debug("getTicketById() start. ID = " + id);
        Ticket ticket = bookingFacade.getTicketById(id);
        model.put(TICKET, ticket);
        LOG.debug("getTicketById() end. Result = " + ticket);
        return TICKET;
    }

    @RequestMapping(value = "/book", params = {"id", "eventId", "userId", "category", "place"}, method = RequestMethod.GET)
    public String bookTicket(@RequestParam Long id, @RequestParam Long eventId, @RequestParam Long userId, @RequestParam Ticket.Category category, @RequestParam int place, ModelMap model) {
        LOG.debug("bookTicket() start - id, eventId, userId, category, place {}", Arrays.asList(id, eventId, userId, category, place));
        Ticket ticket = new TicketImpl(id, eventId, userId, category, place);
        Ticket createdTicket = bookingFacade.bookTicket(ticket);
        model.put(TICKET, createdTicket);
        LOG.debug("bookTicket() end. Result = " + createdTicket);
        return TICKET;
    }

    @RequestMapping(value = "/getByUser", params = {"id", "name", "email", "pageSize", "pageNum"}, method = RequestMethod.GET)
    public String getBookedTickets(@RequestParam Long id, @RequestParam String name, @RequestParam String email, @RequestParam int pageSize, @RequestParam int pageNum, ModelMap model) {
        LOG.debug("getBookedTickets() start - id, name, email, pageSize, pageNum {}", Arrays.asList(id, name, email, pageSize, pageNum));
        User user = new UserImpl(id, name, email);
        List<Ticket> bookedTicketsForUser = bookingFacade.getBookedTickets(user, pageNum, pageSize);
        Tickets tickets = new Tickets();
        tickets.setTickets(bookedTicketsForUser);
        model.put("tickets", tickets);
        LOG.debug("getBookedTickets() end. Result = " + tickets);
        return TICKET;
    }

    @RequestMapping(value = "/getByEvent", params = {"id", "title", "date", "ticketPrice", "pageSize", "pageNum"}, method = RequestMethod.GET)
    public String getBookedTickets(@RequestParam Long id, @RequestParam String title, @RequestParam Date date, @RequestParam int ticketPrice, @RequestParam int pageSize, @RequestParam int pageNum, ModelMap model) {
        LOG.debug("getBookedTickets() start - id, title, date, ticketPrice, pageSize, pageNum {}", Arrays.asList(id, title, date, ticketPrice, pageSize, pageNum));
        Event event = new EventImpl(id, title, date, ticketPrice);
        List<Ticket> bookedTicketsForEvent = bookingFacade.getBookedTickets(event, pageSize, pageNum);
        model.put("tickets", bookedTicketsForEvent);
        LOG.debug("getBookedTickets() end. Result = " + bookedTicketsForEvent);
        return TICKET;
    }

    @RequestMapping(value = "/delete", params = {"id"}, method = RequestMethod.GET)
    public String deleteTicket(@RequestParam("id") Long id, ModelMap model) {
        LOG.debug(String.format("deleteTicket() start. Ticket_id = %d", id));
        boolean result = bookingFacade.cancelTicket(id);
        model.put(TICKET, result);
        LOG.debug("deleteTicket() end. Result = " + result);
        return TICKET;
    }

}
