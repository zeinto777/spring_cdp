package dao.impl;

import dao.IBookingDAO;
import domain.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;


import java.util.*;
import java.util.stream.Collectors;

import static domain.Rating.*;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
@Repository
@PropertySource("classpath:prepareData.properties")
public class BookingDAO implements IBookingDAO {
    private @Value("${coefficientForHighRating}") double coefficientForHighRating;
    private @Value("${coefficientForVipSeats}") double coefficientForVipSeats;
    private Map<Event, List<Ticket>> purchasedTickets = new HashMap<>();
    private List<Ticket> tickets = new ArrayList<>();

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public boolean bookTicket(Ticket ticket) {
        return tickets.add(ticket);
    }

    List<Ticket> getTicketsForEvent(Event event, Date date) {
        return null;
    }

    @Override
    public List<Ticket> getTicketsByUserId(long userId) {
        List<Ticket> userTickets = tickets.stream().filter((ticket) -> ticket.getUserId() == userId).collect(Collectors.toList());
        return userTickets;
    }

    @Override
    public long getTicketPrice(Auditorium auditorium, Event event, DateTime dateTime, List<Long> seats, User user) {
        Long price = event.getTicketPrice();
        if (isEventHaveHighRating(event)) price = (long) (price * coefficientForHighRating);
        if (isVipSeats(auditorium, seats)) price = (long) (price * coefficientForVipSeats);
        return price;
    }

    @Override
    public List<Ticket> getAll() {
        return tickets;
    }

    private boolean isVipSeats(Auditorium auditorium, List<Long> seats) {
        List<Long> vip_seats = auditorium.getVip_seats();
        return vip_seats.containsAll(seats);
    }

    private boolean isEventHaveHighRating(Event event) {
        return (event.getRating().getValue().equals(HIGH.getValue())) ? true : false;
    }
}
