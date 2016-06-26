package com.epam.hw3.dao.impl;

import com.epam.hw3.model.impl.EventImpl;
import com.epam.hw3.dao.EventDAO;
import com.epam.hw3.dao.UserAccountDAO;
import com.epam.hw3.dao.UserDAO;
import com.epam.hw3.model.Event;
import com.epam.hw3.model.Ticket;
import com.epam.hw3.model.impl.TicketImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Andrii_Pinchuk on 11/19/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class TicketDAOImplTest {
    private TicketDAOImpl ticketDAO = new TicketDAOImpl();
    private Date date = new Date("Nov 18, 2015");
    @Mock
    JdbcTemplate jdbcTemplate;
    @Mock
    private UserAccountDAO userAccountDAO;
    @Mock
    private EventDAO eventDAO;
    @Mock
    private UserDAO userDAO;
    @Mock
    private PlatformTransactionManager transactionManager;


    @Before
    public void setup() throws SQLException {
        ticketDAO.setJdbcTemplate(jdbcTemplate);
        ticketDAO.setUserAccountDAO(userAccountDAO);
        ticketDAO.setEventDAO(eventDAO);
        ticketDAO.setUserDAO(userDAO);
        ticketDAO.setTransactionManager(transactionManager);
    }

    @Test
    public void testGetBookedTicketsByEvent() {
        Event event = new EventImpl(4186, "Test_4186", date, 45);
        List<Ticket> expectedList = new ArrayList<>();
        expectedList.add(new TicketImpl(23423, 2343, 4186, Ticket.Category.BAR, 1));
        expectedList.add(new TicketImpl(2324423, 2343, 4186, Ticket.Category.BAR, 1));
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(TicketDAOImpl.TicketMapper.class))).thenReturn(expectedList);

        List<Ticket> resultList = ticketDAO.getBookedTickets(event, 2, 0);

        assertArrayEquals(expectedList.toArray(), resultList.toArray());
    }

    @Test
    public void testGetEventById() {
        Ticket expectedTicket = new TicketImpl(23423, 2342, 12412, Ticket.Category.BAR, 1);
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(TicketDAOImpl.TicketMapper.class))).thenReturn(expectedTicket);

        Ticket resultTicket = ticketDAO.getTicketById(23423);

        assertEquals(expectedTicket, resultTicket);
    }

    @Test
    public void testCancelTicket() {
        Ticket ticket = new TicketImpl(23423, 2342, 12412, Ticket.Category.BAR, 1);
        when(jdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);

        boolean result = ticketDAO.cancelTicket(ticket.getId());

        assertTrue(result);
    }


    @Test
    public void testBookTicketsFromList() {
        List<Ticket> expectedList = new ArrayList<>();
        expectedList.add(new TicketImpl(23423, 2343, 4186, Ticket.Category.BAR, 1));
        expectedList.add(new TicketImpl(2324423, 2343, 4186, Ticket.Category.BAR, 1));

        when(jdbcTemplate.batchUpdate(anyString(), anyList())).thenReturn(new int[1]);

        boolean result = ticketDAO.bookTicketsFromList(expectedList);

        assertTrue(result);
    }
}
