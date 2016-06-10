package com.epam.hw1.dao.impl;

import com.epam.hw1.constants.Constants;
import com.epam.hw1.model.Event;
import com.epam.hw1.model.impl.EventImpl;
import com.epam.hw1.dao.EventDAO;
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
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrii_Pinchuk on 11/17/2015.
 */
@Repository("eventDAO")
public class EventDAOImpl implements EventDAO {
    private static final Logger LOG = LoggerFactory.getLogger(EventDAOImpl.class);
    private static final String GET_EVENT_BY_ID = "SELECT * FROM events WHERE id=?";
    private static final String GET_EVENTS_BY_TITLE = "SELECT * FROM events WHERE title LIKE ? LIMIT ?,?";
    private static final String GET_EVENTS_BY_DATE = "SELECT * FROM events WHERE date=? LIMIT ?,?";
    private static final String DELETE_EVENT = "DELETE FROM events WHERE id=?";
    private static final String UPDATE_EVENT = "UPDATE events SET title=?, date=?, ticket_price=? WHERE id=?";
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Event getEventById(long id) {
        LOG.debug("Get event by ID start. ID = " + id);
        Object[] args = new Object[]{id};
        Event result = jdbcTemplate.queryForObject(GET_EVENT_BY_ID, args, new EventMapper());
        LOG.debug("Get event by ID end. Result = " + result);
        return result;
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        LOG.debug(String.format("Get events by title start. Title = %s, pageSize = %d, pageNum = %d", title, pageSize, pageNum));
        Object[] args = new Object[]{"%" + title + "%", pageNum, pageSize};
        List<Event> result = jdbcTemplate.query(GET_EVENTS_BY_TITLE, args, new EventMapper());
        LOG.debug("Get events by title end. Result = " + result);
        return result;
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        LOG.debug(String.format("Get events for date start. Day = %s, pageSize = %d, pageNum = %d", day, pageSize, pageNum));
        Object[] args = new Object[]{day, pageNum, pageSize};
        List<Event> result = jdbcTemplate.query(GET_EVENTS_BY_DATE, args, new EventMapper());
        LOG.debug("Get events for date end. Result = " + result);
        return result;
    }

    @Override
    public Event createEvent(Event event) {
        LOG.debug(String.format("Create event start. Event =  %s", event));
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName(Constants.EVENTS_TABLE_NAME).usingGeneratedKeyColumns(
                Constants.PRIMARY_KEY);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(Constants.EVENTS_COLUMN_TITLE, event.getTitle());
        parameters.put(Constants.EVENTS_COLUMN_DATE, event.getDate());
        parameters.put(Constants.EVENTS_COLUMN_TICKET_PRICE, event.getTicketPrice());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
                parameters));
        event.setId(((Number) key).intValue());
        LOG.debug("Create event start. Result = " + event);
        return event;
    }

    @Override
    public Event updateEvent(Event event) {
        LOG.debug(String.format("Update event start. Event = %s", event));
        Object[] params = {event.getTitle(), event.getDate(), event.getTicketPrice(), event.getId()};
        int[] types = {Types.VARCHAR, Types.DATE, Types.INTEGER, Types.BIGINT};
        int rows = jdbcTemplate.update(UPDATE_EVENT, params, types);
        LOG.debug("Update event end. Result = " + event);
        return event;
    }

    @Override
    public boolean deleteEvent(long eventId) {
        LOG.debug(String.format("Delete event start. Event_id = %d", eventId));
        Object[] args = new Object[]{eventId};
        boolean result = jdbcTemplate.update(DELETE_EVENT, args) > 0;
        LOG.debug("Delete event end. Result = " + result);
        return result;
    }

    public class EventMapper implements RowMapper<Event> {
        @Override
        public Event mapRow(ResultSet resultSet, int i) throws SQLException {
            Event event = new EventImpl();
            event.setId(resultSet.getLong(Constants.EVENTS_COLUMN_ID));
            event.setTitle(resultSet.getString(Constants.EVENTS_COLUMN_TITLE));
            event.setDate(resultSet.getDate(Constants.EVENTS_COLUMN_DATE));
            event.setTicketPrice(resultSet.getInt(Constants.EVENTS_COLUMN_TICKET_PRICE));
            return event;
        }
    }
}
