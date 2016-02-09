package domain;

import java.util.Date;
import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
public class Ticket {

    private long id;
    private long userId;
    private long eventId;
    private Date date;
    private List<Long> seats;

    public Ticket(long id, long userId, long eventId, Date date, List<Long> seats) {
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
        this.date = date;
        this.seats = seats;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getEventId() {
        return eventId;
    }

    public Date getDate() {
        return date;
    }

    public List<Long> getSeats() {
        return seats;
    }
}
