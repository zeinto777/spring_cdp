package service.impl;

import dao.IBookingDAO;
import domain.Auditorium;
import domain.Event;
import domain.Ticket;
import domain.User;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import service.*;

import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/7/2016.
 */
@Service
public class BookingService implements IBookingService {
    @Autowired
    private IUserService userService;
    @Autowired
    private IEventService eventService;
    @Autowired
    private IAuditoriumService auditoriumService;
    @Autowired @Qualifier("discountService")
    private IDiscountService discountService;
    @Autowired
    private IBookingDAO bookingDAO;

    @Override
    public List<Ticket> getTicketsByUserId(long userId) {
        return bookingDAO.getTicketsByUserId(userId);
    }

    @Override
    public long getTicketPrice(long auditoriumId, long eventId, DateTime dateTime, List<Long> seats, long userId) {
        User user = userService.getById(userId);
        Event event = eventService.getById(eventId);
        Auditorium auditorium = auditoriumService.getById(auditoriumId);
        int discount = discountService.getDiscount(user, getTicketsByUserId(userId));
        return bookingDAO.getTicketPrice(auditorium, event, dateTime, seats, user) * ((100 - discount) / 100);
    }

    @Override
    public List<Ticket> getAll() {
        return bookingDAO.getAll();
    }

    @Override
    public boolean bookTicket(Ticket ticket) {
        return bookingDAO.bookTicket(ticket);
    }
}
