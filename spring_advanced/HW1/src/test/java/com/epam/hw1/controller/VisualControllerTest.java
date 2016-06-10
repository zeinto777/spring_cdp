package com.epam.hw1.controller;

import com.epam.hw1.constants.Constants;
import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.model.Ticket;
import com.epam.hw1.model.impl.TicketImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Created by Andrii_Pinchuk on 12/9/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class VisualControllerTest {
    private VisualController visualController = new VisualController();
    private Date date = new Date("Nov 18, 2015");
    @Mock
    BookingFacade bookingFacade;

    @Before
    public void setup() {
        visualController.setBookingFacade(bookingFacade);
    }

    @Test
    public void testGetEventById() {
        ModelMap model = new ModelMap();
        Ticket expectedTicket = new TicketImpl(23423, 2342, 12412, Ticket.Category.BAR, 1);

        when(bookingFacade.getTicketById(anyLong())).thenReturn(expectedTicket);

        visualController.getTicketById(anyLong(),true, model);
        assertEquals(expectedTicket, model.get(Constants.TICKET));
    }
}
