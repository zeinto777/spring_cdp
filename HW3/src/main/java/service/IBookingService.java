package service;

import domain.Auditorium;
import domain.Event;
import domain.Ticket;
import domain.User;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/7/2016.
 */
public interface IBookingService {

    List<Ticket> getTicketsByUserId(long userId);

    long getTicketPrice(long auditoriumId, long eventId, DateTime dateTime, List<Long> seats, long userId);

    List<Ticket> getAll();

    Ticket bookTicket(Ticket ticket);

}
