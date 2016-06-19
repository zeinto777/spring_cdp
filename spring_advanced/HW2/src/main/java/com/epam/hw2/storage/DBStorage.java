package com.epam.hw2.storage;


import com.epam.hw2.constants.Constants;
import com.epam.hw2.facade.BookingFacade;
import com.epam.hw2.model.Event;
import com.epam.hw2.model.Ticket;
import com.epam.hw2.model.impl.EventImpl;
import com.epam.hw2.model.impl.TicketImpl;
import com.epam.hw2.model.impl.UserAccountImpl;
import com.epam.hw2.model.User;
import com.epam.hw2.model.UserAccount;
import com.epam.hw2.model.impl.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by Andrii_Pinchuk on 11/17/2015.
 */
public class DBStorage {
    @Autowired
    private BookingFacade bookingFacade;
    private long idOfLastEvent;
    private long idOfLastUser;

    public DBStorage() {
    }

    public void write(Map<String, Object> data) {
        for (Map.Entry<String, Object> bufEntry : data.entrySet()) {
            String key = bufEntry.getKey();
            Object value = bufEntry.getValue();
            String prefix = key.split(":")[0];
            addItemToStorage(prefix, value);
        }
    }

    private void addItemToStorage(String prefix, Object value) {
        switch (prefix) {
            case Constants.EVENT:
                Event event = bookingFacade.createEvent((EventImpl) value);
                idOfLastEvent = event.getId();
                break;
            case Constants.USER:
                User user = bookingFacade.createUser((UserImpl) value);
                idOfLastUser = user.getId();
                break;
            case Constants.USER_ACCOUNT:
                UserAccount userAccount = (UserAccountImpl) value;
                userAccount.setUserId(idOfLastUser);
                bookingFacade.createUserAccount(userAccount);
                break;
            case Constants.TICKET:
                Ticket ticket = (TicketImpl) value;
                ticket.setEventId(idOfLastEvent);
                ticket.setUserId(idOfLastUser);
                bookingFacade.bookTicket(ticket);
                break;
        }
    }

}
