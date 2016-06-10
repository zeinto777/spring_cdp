package com.epam.hw1.service.impl;

import com.epam.hw1.model.Event;
import com.epam.hw1.model.Ticket;
import com.epam.hw1.dao.TicketDAO;
import com.epam.hw1.model.User;
import com.epam.hw1.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Andrii_Pinchuk on 11/17/2015.
 */
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketDAO ticketDAO;

    public Ticket bookTicket(Ticket ticket) {
        return ticketDAO.bookTicket(ticket);
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return ticketDAO.getBookedTickets(user, pageSize, pageNum);
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return ticketDAO.getBookedTickets(event, pageSize, pageNum);
    }

    public boolean cancelTicket(long ticketId) {
        return ticketDAO.cancelTicket(ticketId);
    }

    public Ticket getTicketById(long id) {
        return ticketDAO.getTicketById(id);
    }

    public boolean bookTicketsFromList(List<Ticket> tickets) {
        return ticketDAO.bookTicketsFromList(tickets);
    }
}
