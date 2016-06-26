package facade.impl;

import com.epam.hw3.facade.BookingFacade;
import com.epam.hw3.model.Event;
import com.epam.hw3.model.Ticket;
import com.epam.hw3.model.User;
import com.epam.hw3.model.UserAccount;
import com.epam.hw3.model.impl.EventImpl;
import com.epam.hw3.model.impl.TicketImpl;
import com.epam.hw3.model.impl.UserAccountImpl;
import com.epam.hw3.model.impl.UserImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Andrii_Pinchuk on 11/19/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/config.xml")
@Transactional
public class BookingFacadeImplTest {
    @Autowired
    private Date date;
    @Autowired
    private BookingFacade facade;

    @Test
    @Rollback
    public void testCreateEventExpectedAddNewObjectToDB() {
        Event expectedEvent = new EventImpl(4186, "Test_4186", date, 45);

        Event resultEvent = facade.createEvent(expectedEvent);
        expectedEvent.setId(expectedEvent.getId());

        assertEquals(expectedEvent, resultEvent);
    }


    @Test
    @Rollback
    public void testGetEventByIdExpectedReceiveEventFromDB() {
        Event event = new EventImpl(4186, "Test_4186", date, 45);

        Event resultEvent = facade.createEvent(event);

        assertNotNull(resultEvent);
    }


    @Test
    @Rollback
    public void testGetEventsByTitleAddEventsToDBExpectedListOfEvents() {
        List<Event> expectedEvents = new ArrayList<>();
        expectedEvents.add(new EventImpl(4186, "Test_4186", date, 45));
        expectedEvents.add(new EventImpl(4187, "Test_4187", date, 46));

        facade.createEvent(expectedEvents.get(0));
        facade.createEvent(expectedEvents.get(1));

        List<Event> resultEvents = facade.getEventsByTitle("Test_", 2, 0);

        assertArrayEquals(expectedEvents.toArray(), resultEvents.toArray());
    }


    @Test
    @Rollback
    public void testDeleteEventExpectedDeleteEventFromDB() {
        Event event = new EventImpl(4186, "Test_4186", date, 45);

        Event resultEvent = facade.createEvent(event);

        boolean result = facade.deleteEvent(resultEvent.getId());
        assertTrue(result);
    }


    @Test
    @Rollback
    public void testUpdateEventExpectedChangeEventInDB() {
        Event event = new EventImpl(4186, "Test_4186", date, 45);
        Event expectedEvent = facade.createEvent(event);
        expectedEvent.setTitle("UpdateTitle");
        expectedEvent.setTicketPrice(50);

        facade.updateEvent(expectedEvent);

        Event resultEvent = facade.getEventById(expectedEvent.getId());
        assertEquals(expectedEvent, resultEvent);
    }

/*USER*/


    @Test
    @Rollback
    public void testCreateUserExpectedAddNewObjectToDB() {
        User expectedUser = new UserImpl(4186, "Andrii Pinchuk", "Andrii_Pinchuk@epam.com");

        User resultUser = facade.createUser(expectedUser);
        expectedUser.setId(resultUser.getId());

        assertEquals(expectedUser, resultUser);
    }

    @Test
    @Rollback
    public void testGetUsertByIdExpectedReceiveUserFromDB() {
        User user = new UserImpl(4186, "Andrii Pinchuk", "Andrii_Pinchuk@epam.com");
        User expectedUser = facade.createUser(user);

        User resultUser = facade.getUserById(expectedUser.getId());

        assertEquals(expectedUser, resultUser);
    }

    @Test
    @Rollback
    public void testDeleteUserExpectedDeleteUSerFromDB() {
        User user = new UserImpl(4186, "Andrii Pinchuk", "Andrii_Pinchuk@epam.com");

        User resultUser = facade.createUser(user);

        boolean result = facade.deleteUser(resultUser.getId());
        assertTrue(result);
    }

    @Test
    @Rollback
    public void testUpdateUserExpectedChangeUserInDB() {
        User user = new UserImpl(4186, "Andrii Pinchuk", "Andrii_Pinchuk@epam.com");
        User expectedUsers = facade.createUser(user);
        expectedUsers.setName("UpdateName");
        expectedUsers.setEmail("UpdateName@epam.com");

        facade.updateUser(expectedUsers);

        User resultUser = facade.getUserById(expectedUsers.getId());
        assertEquals(expectedUsers, resultUser);
    }

    @Test
    @Rollback
    public void testGetUsersByEmail() {
        User user = new UserImpl(4186, "Andrii Pinchuk", "Test@epam.com");
        User expectedUser = facade.createUser(user);

        User resultUser = facade.getUserByEmail("Test@epam.com");

        assertEquals(expectedUser, resultUser);
    }


    @Test
    @Rollback
    public void testGetUserByName() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new UserImpl(4186, "Andrii Pinchuk_Test", "Andrii_Pinchuk@epam.com"));
        expectedUsers.add(new UserImpl(4187, "Andrii Pinchuk_Test", "Andrii_Pinchuk@epam.com"));

        facade.createUser(expectedUsers.get(0));
        facade.createUser(expectedUsers.get(1));

        List<User> resultUsers = facade.getUsersByName("Andrii Pinchuk_Test", 2, 0);

        assertArrayEquals(expectedUsers.toArray(), resultUsers.toArray());
    }


    /*TICKET*/
    @Test
    @Rollback
    public void testBookTicket() {
        Event event = new EventImpl(4186, "Test_4186", date, 45);
        User user = new UserImpl(4187, "Andrii Pinchuk", "Test@epam.com");
        UserAccount userAccount = new UserAccountImpl(4188, 4187, 55);

        Event createEvent = facade.createEvent(event);
        User createUser = facade.createUser(user);
        userAccount.setUserId(createUser.getId());
        UserAccount createUserAccount = facade.createUserAccount(userAccount);

        Ticket expectedTicket = new TicketImpl(23423, event.getId(), user.getId(), Ticket.Category.BAR, 1);
        Ticket resultTicket = facade.bookTicket(expectedTicket);
        expectedTicket.setId(resultTicket.getId());

        assertEquals(expectedTicket, resultTicket);
    }

    @Test
    @Rollback
    public void testGetBookedTicketsWhenSetDefaultUserExpectedReturnTicketWithUserDefaultID() {
        Event event = facade.createEvent(new EventImpl(4186, "Test_4186", date, 45));
        User defaultUser = facade.createUser(new UserImpl(1, "Default_User", "Default_User@epam.com"));
        UserAccount userAccount = facade.createUserAccount(new UserAccountImpl(4188, defaultUser.getId(), 55));
        facade.setDefaultUser(defaultUser);
        Ticket ticket = facade.bookTicket(new TicketImpl(23423, event.getId(), defaultUser.getId(), Ticket.Category.BAR, 2));
        User user = facade.createUser(new UserImpl(4188, "Andrii Pinchuk", "Andrii_Pinchuk@epam.com"));
        UserAccount userAccount2 = facade.createUserAccount(new UserAccountImpl(4188, user.getId(), 55));
        Ticket ticket2 = facade.bookTicket(new TicketImpl(2, event.getId(), user.getId(), Ticket.Category.PREMIUM, 2));

        List<Ticket> result = facade.getBookedTickets(user, 2, 0);

        assertEquals(defaultUser.getId(), result.get(0).getUserId());
    }


}
