package dao;

import domain.Auditorium;
import domain.Event;
import domain.Ticket;
import domain.User;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/7/2016.
 */
public interface IBookingDAO {
    List<Ticket> getTicketsByUserId(long userId);

    long getTicketPrice(Auditorium auditoriumm, Event event, DateTime dateTime, List<Long> seats, User user);

    List<Ticket> getAll();

    Ticket bookTicket(Ticket ticket);
}
