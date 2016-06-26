package com.epam.hw3.service;

import com.epam.hw3.model.Event;
import com.epam.hw3.model.Ticket;
import com.epam.hw3.model.User;

import java.util.List;

/**
 * Created by Andrii_Pinchuk on 11/17/2015.
 */
public interface TicketService {

    /**
     * Book ticket for a specified event on behalf of specified user.
     *
     * @param  ticket   Ticket Id.
     * @return Ticket ticket object.
     * @throws java.lang.IllegalStateException if this place has already been booked.
     */
    Ticket bookTicket(Ticket ticket);

    /**
     * Get all booked tickets for specified user. Tickets should be sorted by event date in descending order.
     *
     * @param user     User
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     */
    List<Ticket> getBookedTickets(User user, int pageSize, int pageNum);

    /**
     * Get all booked tickets for specified event. Tickets should be sorted in by user email in ascending order.
     *
     * @param event    Event
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     */
    List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum);

    /**
     * Cancel ticket with a specified id.
     *
     * @param ticketId Ticket id.
     * @return Flag whether anything has been canceled.
     */
    boolean cancelTicket(long ticketId);


    Ticket getTicketById(long id);

    boolean bookTicketsFromList(List<Ticket> tickets);
}
