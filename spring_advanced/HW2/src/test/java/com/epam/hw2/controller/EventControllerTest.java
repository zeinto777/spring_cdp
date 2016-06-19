package com.epam.hw2.controller;

import com.epam.hw2.constants.Constants;
import com.epam.hw2.facade.BookingFacade;
import com.epam.hw2.model.Event;
import com.epam.hw2.model.impl.EventImpl;
import com.epam.hw2.model.wrapper.Events;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

/**
 * Created by Andrii_Pinchuk on 12/9/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class EventControllerTest {
    private EventController eventController = new EventController();
    private Date date = new Date("Nov 18, 2015");
    @Mock
    BookingFacade bookingFacade;

    @Before
    public void setup() {
        eventController.setBookingFacade(bookingFacade);
    }

    @Test
    public void testGetEventById() throws Exception {
        ModelMap model = new ModelMap();
        Event expectedEvent = new EventImpl(4186, "Test_4186", date, 45);
        when(bookingFacade.getEventById(anyLong())).thenReturn(expectedEvent);

        eventController.getEventById(anyLong(), model);

        assertEquals(expectedEvent, model.get(Constants.EVENT));
    }


    @Test
    public void testGetEventsByTitle() {
        ModelMap model = new ModelMap();
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new EventImpl(4186, "Test_4186", date, 45));
        expectedEvents.add(new EventImpl(4187, "Test_4187", date, 46));

        when(bookingFacade.getEventsByTitle(anyString(), anyInt(), anyInt())).thenReturn(expectedEvents);

        eventController.getEventsByTitle(anyString(), anyInt(), anyInt(), model);

        Events events = (Events) model.get(Constants.EVENTS);
        assertArrayEquals(expectedEvents.toArray(), (events.getEvents()).toArray());
    }

    @Test
    public void testDeleteEventExpectedDeleteEventFromDB() {
        ModelMap model = new ModelMap();
        Event event = new EventImpl(4186, "Test_4186", date, 45);

        when(bookingFacade.deleteEvent(anyLong())).thenReturn(true);

        eventController.deleteEvent(anyLong(), model);

        assertTrue((Boolean) model.get(Constants.EVENT));
    }


    @Test
    public void testUpdateEventExpectedChangeEventInDB() {
        ModelMap model = new ModelMap();
        Event event = new EventImpl(4186, "Test_4186", date, 45);

        when(bookingFacade.updateEvent(any(Event.class))).thenReturn(event);

        eventController.updateEvent(anyLong(), "Test_4186", date, 45, model);

        assertEquals(event, model.get(Constants.EVENT));
    }

    @Test
    public void testGetEventsForDay() {
        ModelMap model = new ModelMap();
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new EventImpl(4186, "Test_4186", date, 45));
        expectedEvents.add(new EventImpl(4187, "Test_4187", date, 46));

        when(bookingFacade.getEventsForDay(any(Date.class), anyInt(), anyInt())).thenReturn(expectedEvents);


        eventController.getEventsForDay(any(Date.class), anyInt(), anyInt(), model);

        Events events = (Events) model.get(Constants.EVENTS);
        assertArrayEquals(expectedEvents.toArray(), (events.getEvents()).toArray());
    }


    @Test
    public void testCreateEvent() {
        ModelMap model = new ModelMap();
        Event event = new EventImpl(4186, "Test_4186", date, 45);

        when(bookingFacade.createEvent(any(Event.class))).thenReturn(event);

        eventController.createEvent(anyLong(), "Test_4186", date, 45, model);
        assertEquals(event, model.get(Constants.EVENT));

    }
}
