package com.epam.hw2.facade.impl;

import com.epam.hw2.facade.BookingFacade;
import com.epam.hw2.model.Event;
import com.epam.hw2.model.Ticket;
import com.epam.hw2.service.TicketService;
import com.epam.hw2.bean.DefaultDataProvider;
import com.epam.hw2.model.User;
import com.epam.hw2.model.UserAccount;
import com.epam.hw2.service.EventService;
import com.epam.hw2.service.UserAccountService;
import com.epam.hw2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by Andrii_Pinchuk on 11/17/2015.
 */
public class BookingFacadeImpl implements BookingFacade {

    private EventService eventService;
    private TicketService ticketService;
    private UserService userService;
    private UserAccountService userAccountService;
    @Autowired
    private DefaultDataProvider defaultDataProvider;

    @Autowired
    public BookingFacadeImpl(EventService eventService, TicketService ticketService, UserService userService, UserAccountService userAccountService) {
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.userService = userService;
        this.userAccountService = userAccountService;
    }

    public void setDefaultDataProvider(DefaultDataProvider defaultDataProvider) {
        this.defaultDataProvider = defaultDataProvider;
    }

    /*EVENT*/

    public Event getEventById(long id) {
        return eventService.getEventById(id);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventService.getEventsByTitle(title, pageSize, pageNum);
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return eventService.getEventsForDay(day, pageSize, pageNum);
    }

    public Event createEvent(Event event) {
        return eventService.createEvent(event);
    }

    public Event updateEvent(Event event) {
        return eventService.updateEvent(event);
    }

    public boolean deleteEvent(long eventId) {
        return eventService.deleteEvent(eventId);
    }

    /*USER*/

    public User getUserById(long id) {
        return userService.getUserById(id);
    }

    public User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return userService.getUsersByName(name, pageSize, pageNum);
    }

    public User createUser(User user) {
        return userService.createUser(user);
    }

    public User updateUser(User user) {
        return userService.updateUser(user);
    }

    public boolean deleteUser(long userId) {
        return userService.deleteUser(userId);
    }

    /*TICKET*/

    public Ticket bookTicket(Ticket ticket) {
        return ticketService.bookTicket(ticket);
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(user, pageSize, pageNum);
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(event, pageSize, pageNum);
    }

    public boolean cancelTicket(long ticketId) {
        return ticketService.cancelTicket(ticketId);
    }

    public Ticket getTicketById(long id) {
        return ticketService.getTicketById(id);
    }


    /*USER_ACCOUNT*/

    @Override
    public UserAccount getUserAccountByUserId(long id) {
        return userAccountService.getUserAccountByUserId(id);
    }

    @Override
    public UserAccount updateUserAccount(UserAccount currentUserAccount) {
        return userAccountService.updateUserAccount(currentUserAccount);
    }

    public UserAccount createUserAccount(UserAccount userAccount) {
        return userAccountService.createUserAccount(userAccount);
    }

    @Override
    public boolean deleteUserAccount(long userAccountId) {
        return userAccountService.deleteUserAccount(userAccountId);
    }

    @Override
    public void setDefaultUser(User user) {
        defaultDataProvider.setDefaultUser(user);
    }

    @Override
    public void setDefaultEvent(Event event) {
        defaultDataProvider.setDefaultEvent(event);
    }
}
