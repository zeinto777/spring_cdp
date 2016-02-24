package dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Andrii_Pinchuk on 2/23/2016.
 */
@Repository
public class CounterAspectDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String UPDATE_EVENT = "UPDATE event_counter as t1,(SELECT * FROM event_counter WHERE eventId=%d) as t2 SET t1.%s=t2.%s+1 WHERE t1.eventId=%d";
    private static final String INSERT_EVENT = "INSERT INTO `event_counter`(`eventId`, `getEventByName`, `getTicketPrice`, `bookedTicket`) values(?,0,0,0)";

    public void update(String eventName, Long eventId) {
        String query = String.format(UPDATE_EVENT, eventId, eventName, eventName, eventId);
        try {
            jdbcTemplate.update(query, new Object[]{});
        } catch (EmptyResultDataAccessException e) {
            jdbcTemplate.update(INSERT_EVENT, new Object[]{eventId});
        }
    }
}
