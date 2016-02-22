package domain;


import java.sql.Date;
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

    public Ticket() {
    }

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

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSeats(List<Long> seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;

        Ticket ticket = (Ticket) o;
        if (id != ticket.id) return false;
        if (userId != ticket.userId) return false;
        if (eventId != ticket.eventId) return false;


        /*date = DateUtil.parseDate(date.toString());
        ticket.date = DateUtil.parseDate( ticket.date.toString());*/
        if (date != null ? !date.equals(ticket.date) : ticket.date != null) return false;
        return !(seats != null ? !seats.equals(ticket.seats) : ticket.seats != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (eventId ^ (eventId >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (seats != null ? seats.hashCode() : 0);
        return result;
    }
}
