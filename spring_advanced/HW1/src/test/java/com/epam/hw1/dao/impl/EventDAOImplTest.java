package com.epam.hw1.dao.impl;

import com.epam.hw1.model.Event;
import com.epam.hw1.model.impl.EventImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Andrii_Pinchuk on 11/18/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class EventDAOImplTest {
    private EventDAOImpl eventDAO = new EventDAOImpl();
    private Date date = new Date("Nov 18, 2015");

    @Mock
    JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        eventDAO.setJdbcTemplate(jdbcTemplate);
    }

    @Test
    public void testGetEventById() {
        Event expectedEvent = new EventImpl(4186, "Test_4186", date, 45);
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(EventDAOImpl.EventMapper.class))).thenReturn(expectedEvent);

        Event resultEvent = eventDAO.getEventById(4186);

        assertEquals(expectedEvent, resultEvent);
    }

    @Test
    public void testGetEventsByTitle() {
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new EventImpl(4186, "Test_4186", date, 45));
        expectedEvents.add(new EventImpl(4187, "Test_4187", date, 46));

        when(jdbcTemplate.query(anyString(), any(Object[].class), any(EventDAOImpl.EventMapper.class))).thenReturn(expectedEvents);

        List<Event> resultEvents = eventDAO.getEventsByTitle("Test_", 2, 0);

        assertArrayEquals(expectedEvents.toArray(), resultEvents.toArray());
    }

    @Test
    public void testDeleteEventExpectedDeleteEventFromDB() {
        Event event = new EventImpl(4186, "Test_4186", date, 45);

        when(jdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);

        boolean result = eventDAO.deleteEvent(event.getId());
        assertTrue(result);
    }

    @Test
    public void testUpdateEventExpectedChangeEventInDB() {
        Event event = new EventImpl(4186, "Test_4186", date, 45);

        when(jdbcTemplate.update(anyString(), any(Object[].class), Matchers.<int[]>any())).thenReturn(1);

        Event resultEvent = eventDAO.updateEvent(event);

        assertEquals(event, resultEvent);
    }

    @Test
    public void testGetEventsForDay() {
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new EventImpl(4186, "Test_4186", date, 45));
        expectedEvents.add(new EventImpl(4187, "Test_4187", date, 46));

        when(jdbcTemplate.query(anyString(), any(Object[].class), any(EventDAOImpl.EventMapper.class))).thenReturn(expectedEvents);

        List<Event> actualResult = eventDAO.getEventsForDay(date, 2, 0);

        assertArrayEquals(expectedEvents.toArray(), actualResult.toArray());
    }
}
