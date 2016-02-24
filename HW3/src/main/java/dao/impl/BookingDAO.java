package dao.impl;

import dao.IBookingDAO;
import domain.Auditorium;
import domain.Event;
import domain.Ticket;
import domain.User;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static constants.Constants.*;
import static domain.Rating.HIGH;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
@Repository
@PropertySource("classpath:prepareData.properties")
public class BookingDAO implements IBookingDAO {
    private
    @Value("${coefficientForHighRating}")
    double coefficientForHighRating;
    private
    @Value("${coefficientForVipSeats}")
    double coefficientForVipSeats;
    private static final Logger LOG = LoggerFactory.getLogger(BookingDAO.class);
    private static final String GET_ALL = "SELECT * FROM tickets";
    private static final String GET_TICKET_BY_ID = "SELECT * FROM tickets WHERE userId=?";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Ticket bookTicket(Ticket ticket) {
        LOG.debug(String.format("Book ticket. Ticket - %s", ticket));
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName(TICKETS_TABLE_NAME).usingGeneratedKeyColumns(
                PRIMARY_KEY);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(TICKETS_COLUMN_USER_ID, ticket.getUserId());
        parameters.put(TICKETS_COLUMN_EVENT_ID, ticket.getEventId());
        parameters.put(TICKETS_COLUMN_DATE, ticket.getDate());

        String listString = ticket.getSeats().stream().map(Object::toString)
                .collect(Collectors.joining(","));
        parameters.put(TICKETS_COLUMN_SEATS, listString);
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
                parameters));
        ticket.setId((key).intValue());
        LOG.debug("Book ticket end. Result = " + ticket);
        return ticket;
    }

    List<Ticket> getTicketsForEvent(Event event, Date date) {
        return null;
    }

    @Override
    public List<Ticket> getTicketsByUserId(long userId) {
        LOG.debug("Get ticket by ID start. ID = " + userId);
        Object[] args = new Object[]{userId};
        List<Ticket> result = jdbcTemplate.query(GET_TICKET_BY_ID, args, new TicketMapper());
        LOG.debug("Get ticket by ID end. Result = " + result);
        return result;
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
        LOG.debug(String.format("Get all tickets.Start."));
        Object[] args = new Object[]{};
        List<Ticket> result = jdbcTemplate.query(GET_ALL, args, new TicketMapper());
        LOG.debug("Get all tickets end. Result = " + result);
        return result;
    }

    private boolean isVipSeats(Auditorium auditorium, List<Long> seats) {
        List<Long> vip_seats = auditorium.getVip_seats();
        return vip_seats.containsAll(seats);
    }

    private boolean isEventHaveHighRating(Event event) {
        return (event.getRating().getValue().equals(HIGH.getValue())) ? true : false;
    }


    public class TicketMapper implements RowMapper<Ticket> {
        @Override
        public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
            Ticket ticket = new Ticket();
            ticket.setId(resultSet.getLong(TICKETS_COLUMN_ID));
            ticket.setUserId(resultSet.getLong(TICKETS_COLUMN_USER_ID));
            ticket.setEventId(resultSet.getLong(TICKETS_COLUMN_EVENT_ID));
            ticket.setDate(resultSet.getDate(TICKETS_COLUMN_DATE));
            List<Long> list = Arrays.asList(resultSet.getString(TICKETS_COLUMN_SEATS).split(",")).stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
            ticket.setSeats(list);
            return ticket;
        }
    }

}
