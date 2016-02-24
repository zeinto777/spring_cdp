package dao.impl;

import dao.IEventDAO;
import domain.Event;
import domain.Rating;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import static constants.Constants.*;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
@Repository
public class EventDAO implements IEventDAO {
    private static final Logger LOG = LoggerFactory.getLogger(EventDAO.class);
    private static final String GET_EVENT_BY_ID = "SELECT * FROM events WHERE id=?";
    private static final String GET_EVENTS_ALL = "SELECT * FROM events";
    private static final String GET_EVENTS_BY_NAME = "SELECT * FROM events WHERE name LIKE ?";
    private static final String DELETE_EVENT = "DELETE FROM events WHERE id=?";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean create(Event event) {
        LOG.debug(String.format("Create event start. Event - %s", event));
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName(EVENTS_TABLE_NAME).usingGeneratedKeyColumns(
                PRIMARY_KEY);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(EVENTS_COLUMN_NAME, event.getName());
        parameters.put(EVENTS_COLUMN_TICKET_PRICE, event.getTicketPrice());
        parameters.put(EVENTS_COLUMN_RATING, event.getRating().getValue());
        parameters.put(EVENTS_COLUMN_DURATION, new Timestamp(event.getDuration().getMillis()));
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
                parameters));
        event.setId((key).intValue());
        LOG.debug("Create user end. Result = " + event);
        return true;
    }

    @Override
    public boolean remove(long eventId) {
        LOG.debug(String.format("Delete event start. Event_id = %d", eventId));
        Object[] args = new Object[]{eventId};
        boolean result = jdbcTemplate.update(DELETE_EVENT, args) > 0;
        LOG.debug("Delete event end. Result = " + result);
        return result;
    }

    @Override
    public List<Event> getByName(String name) {
        LOG.debug(String.format("Get events by name start. Name = %s", name));
        Object[] args = new Object[]{"%" + name + "%"};
        List<Event> result = jdbcTemplate.query(GET_EVENTS_BY_NAME, args, new EventMapper());
        LOG.debug("Get events by name end. Result = " + result);
        return result;
    }

    @Override
    public Set<Event> getAll() {
        LOG.debug(String.format("Get events all events."));
        Object[] args = new Object[]{};
        Set<Event> result = new HashSet<>(jdbcTemplate.query(GET_EVENTS_ALL, args, new EventMapper()));
        LOG.debug("Get events by name end. Result = " + result);
        return result;
    }

    @Override
    public Event getById(long eventId) {
        LOG.debug("Get event by ID start. ID = " + eventId);
        Object[] args = new Object[]{eventId};
        Event result = jdbcTemplate.queryForObject(GET_EVENT_BY_ID, args, new EventMapper());
        LOG.debug("Get event by ID end. Result = " + result);
        return result;
    }

    public class EventMapper implements RowMapper<Event> {
        @Override
        public Event mapRow(ResultSet resultSet, int i) throws SQLException {
            Event event = new Event();
            event.setId(resultSet.getLong(EVENTS_COLUMN_ID));
            event.setName(resultSet.getString(EVENTS_COLUMN_NAME));
            event.setTicketPrice(resultSet.getLong(EVENTS_COLUMN_TICKET_PRICE));
            event.setRating(Rating.valueOf(resultSet.getString(EVENTS_COLUMN_RATING).toUpperCase()));
            event.setDuration(new DateTime(resultSet.getTimestamp(EVENTS_COLUMN_DURATION)));
            return event;
        }
    }

}
