package service.impl;

import domain.Event;
import domain.Rating;
import domain.User;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import service.IBookingService;
import service.IEventService;
import service.IUserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Andrii_Pinchuk on 2/9/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring.xml")
@Transactional
public class BookingServiceITTest {

    @Autowired
    private IBookingService bookingService;


    @Autowired
    private IUserService userService;
    @Autowired
    private IEventService eventService;

    @Before
    public void setUp() {
        User user = new User(1L, "test@gmail.com", "andrii", parseDate("2014-02-14"));
        Event event = new Event(1L,"movie_1", 55, Rating.HIGH,new DateTime(2016, 9, 3, 12, 5, 0, 0));


        userService.register(user);
        eventService.create(event);
    }


    @Test
    public void testGetTicketsByUserId() {


    }


    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-mm-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
