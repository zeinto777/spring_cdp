package com.epam.hw3.controller;

import com.epam.hw3.constants.Constants;
import com.epam.hw3.facade.BookingFacade;
import com.epam.hw3.model.Event;
import com.epam.hw3.model.Ticket;
import com.epam.hw3.model.impl.EventImpl;
import com.epam.hw3.model.impl.TicketImpl;
import com.epam.hw3.model.impl.UserImpl;
import com.epam.hw3.model.User;
import com.epam.hw3.model.wrapper.Tickets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

/**
 * Created by Andrii_Pinchuk on 12/9/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class TicketControllerTest {
    private TicketController ticketController = new TicketController();
    private Date date = new Date("Nov 18, 2015");
    @Mock
    BookingFacade bookingFacade;

    @Before
    public void setup() {
        ticketController.setBookingFacade(bookingFacade);
    }


    @Test
    public void testGetBookedTicketsByEvent() {
        ModelMap model = new ModelMap();
        Event event = new EventImpl(4186, "Test_4186", date, 45);
        List<Ticket> expectedList = new ArrayList<>();
        expectedList.add(new TicketImpl(23423, 2343, 4186, Ticket.Category.BAR, 1));
        expectedList.add(new TicketImpl(2324423, 2343, 4186, Ticket.Category.BAR, 1));

        when(bookingFacade.getBookedTickets(any(Event.class), anyInt(), anyInt())).thenReturn(expectedList);

        ticketController.getBookedTickets(anyLong(), "Test_4186", date, 45, anyInt(), anyInt(), model);
        assertArrayEquals(expectedList.toArray(), ((List<Ticket>) model.get(Constants.TICKETS)).toArray());
    }


    @Test
    public void testGetBookedTicketsByUser() {
        ModelMap model = new ModelMap();
        User user = new UserImpl(4186, "Andrii Pinchuk", "Andrii_Pinchuk@epam.com");
        List<Ticket> expectedList = new ArrayList<>();
        expectedList.add(new TicketImpl(23423, 2343, 4186, Ticket.Category.BAR, 1));
        expectedList.add(new TicketImpl(2324423, 2343, 4186, Ticket.Category.BAR, 1));

        when(bookingFacade.getBookedTickets(any(User.class), anyInt(), anyInt())).thenReturn(expectedList);


        ticketController.getBookedTickets(anyLong(), "Andrii Pinchuk", "Andrii_Pinchuk@epam.com", anyInt(), anyInt(), model);

        Tickets tickets = (Tickets) model.get(Constants.TICKETS);
        assertArrayEquals(expectedList.toArray(), (tickets.getTickets()).toArray());
    }


    @Test
    public void testGetEventById() {
        ModelMap model = new ModelMap();
        Ticket expectedTicket = new TicketImpl(23423, 2342, 12412, Ticket.Category.BAR, 1);

        when(bookingFacade.getTicketById(anyLong())).thenReturn(expectedTicket);

        ticketController.getTicketById(anyLong(), model);
        assertEquals(expectedTicket, model.get(Constants.TICKET));
    }


    @Test
    public void testCancelTicket() {
        ModelMap model = new ModelMap();
        Ticket ticket = new TicketImpl(23423, 2342, 12412, Ticket.Category.BAR, 1);

        when(bookingFacade.cancelTicket(anyLong())).thenReturn(true);

        ticketController.deleteTicket(anyLong(), model);

        assertTrue((Boolean) model.get(Constants.TICKET));
    }

}
