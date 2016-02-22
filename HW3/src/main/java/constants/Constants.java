package constants;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/16/2016.
 */
public class Constants {
    private Constants() {

    }

    public static final String USERS_TABLE_NAME = "users";
    public static final String EVENTS_TABLE_NAME = "events";
    public static final String SEANCES_TABLE_NAME = "seances";
    public static final String TICKETS_TABLE_NAME = "tickets";
    public static final String PRIMARY_KEY = "Primary_key";

    public static final String BIRTHDAY = "birthday";
    public static final String GET_EVENT_BY_NAME = "getEventByName";
    public static final String GET_TICKET_PRICE = "getTicketPrice";
    public static final String BOOKED_TICKET = "bookedTicket";

    /*users*/
    public static final String USERS_COLUMN_ID = "id";
    public static final String USERS_COLUMN_NAME = "name";
    public static final String USERS_COLUMN_EMAIL = "email";
    public static final String USERS_COLUMN_BIRTHDAY = "birthday";

    /*events*/
    public static final String EVENTS_COLUMN_ID = "id";
    public static final String EVENTS_COLUMN_NAME = "name";
    public static final String EVENTS_COLUMN_TICKET_PRICE = "ticketPrice";
    public static final String EVENTS_COLUMN_RATING = "rating";
    public static final String EVENTS_COLUMN_DURATION = "duration";

    /*auditoriums*/
    public static final String AUDITORIUMS_COLUMN_ID = "id";
    public static final String AUDITORIUMS_COLUMN_NAME = "name";
    public static final String AUDITORIUMS_COLUMN_NUMBER_OF_SEATS = "number_of_seats";
    public static final String AUDITORIUMS_COLUMN_VIP_SEATS = "vip_seats";

    /*seances*/
    public static final String SEANCES_COLUMN_ID = "id";
    public static final String SEANCES_COLUMN_EVENT_ID = "eventId";
    public static final String SEANCES_COLUMN_AUDITORIUM_ID = "auditoriumId";
    public static final String SEANCES_COLUMN_DATETIME = "dateTime";

    /*tickets*/
    public static final String TICKETS_COLUMN_ID = "id";
    public static final String TICKETS_COLUMN_USER_ID = "userId";
    public static final String TICKETS_COLUMN_EVENT_ID = "eventId";
    public static final String TICKETS_COLUMN_DATE = "date";
    public static final String TICKETS_COLUMN_SEATS = "seats";


}
