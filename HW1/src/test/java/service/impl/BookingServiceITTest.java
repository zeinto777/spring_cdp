package service.impl;

import domain.Event;
import domain.Rating;
import domain.Ticket;
import domain.User;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import service.IAuditoriumService;
import service.IBookingService;
import service.IEventService;
import service.IUserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static util.DateUtil.*;

/**
 * Created by Andrii_Pinchuk on 2/9/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/context.xml")
@Transactional
public class BookingServiceITTest {
    @Autowired
    private IBookingService bookingService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IEventService eventService;
    @Autowired
    private IAuditoriumService auditoriumService;

    @Before
    public void setUp() {
        User user = new User(1L, "test@gmail.com", "andrii", parseDate("2014-02-14"));
        Event event = new Event(1L, "movie_1", 55, Rating.HIGH, new DateTime(2016, 9, 3, 12, 5, 0, 0));
        userService.register(user);
        eventService.create(event);
        eventService.assignAuditorium(1L, 4186, new DateTime(2016, 9, 3, 12, 5, 0, 0));
    }

    @Test
    @Rollback
    public void testGetTicketPrice_expectedIncreasePrice_IfEventHaveHighRating() {
        long expected = 66;
        List<Long> seats = asList(new Long(23));

        long result = bookingService.getTicketPrice(4186, 1L, new DateTime(2016, 9, 3, 12, 5, 0, 0), seats, 1L);

        assertEquals(expected, result);
    }

    @Test
    @Rollback
    public void testGetTicketPrice_expectedDiscountIfTodayUserBirthday() {
        long expected = 62;
        List<Long> seats = asList(new Long(23));
        User user = new User(2L, "test@gmail.com", "andrii", new Date());
        userService.register(user);

        Date currentDate = GregorianCalendar.getInstance().getTime();
        long result = bookingService.getTicketPrice(4186, 1L, new DateTime(currentDate), seats, 2L);

        assertEquals(expected, result);
    }

    @Test
    @Rollback
    public void testGetTicketPrice_expectedIncreasePrice_IfUserOrderVipSeats() {
        long expected = 132;
        List<Long> seats = asList(new Long(45));

        long result = bookingService.getTicketPrice(4186, 1L, new DateTime(2016, 9, 3, 12, 5, 0, 0), seats, 1L);

        assertEquals(expected, result);
    }

    @Test
    @Rollback
    public void testBookTicket_expectedAddTicketToTicketList() {
        List<Long> seats = asList(new Long(23));
        Ticket ticket = new Ticket(1l, 1L, 1L, parseDate("2014-02-14"), seats);

        boolean result = bookingService.bookTicket(ticket);
        List<Ticket> ticketsByUserId = bookingService.getTicketsByUserId(1l);

        assertTrue(result);
        assertEquals(ticket, ticketsByUserId.get(0));
    }


    @Test
    @Rollback
    public void testGetTicketsByUserId_ExpectedReturnPurchasedUserTickets() {
        List<Long> seats = asList(new Long(23));
        Ticket ticket_1 = new Ticket(1l, 1L, 1L, parseDate("2014-02-14"), seats);
        Ticket ticket_2 = new Ticket(1l, 1L, 1L, parseDate("2014-02-14"), seats);
        List<Ticket> expectedTickets = asList(ticket_1, ticket_2);

        bookingService.bookTicket(ticket_1);
        bookingService.bookTicket(ticket_2);

        List<Ticket> resultTickets = bookingService.getTicketsByUserId(1l);
        assertArrayEquals(expectedTickets.toArray(), resultTickets.toArray());
    }

    @Test
    @Rollback
    public void testAssignAuditorium_ExpectedFalseIfAuditoriumIsAlreadyBooked() {
        boolean result = eventService.assignAuditorium(1L, 4186, new DateTime(2016, 9, 3, 12, 5, 0, 0));

        assertFalse(result);
    }

    @Test
    @Rollback
    public void testAssignAuditorium_ExpectedTrueIfAuditoriumIsNotBooked() {
        boolean result = eventService.assignAuditorium(1L, 4186, new DateTime(2017, 9, 3, 12, 5, 0, 0));

        assertTrue(result);
    }
}
