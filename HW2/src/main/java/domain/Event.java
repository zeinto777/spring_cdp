package domain;

import org.joda.time.DateTime;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
public class Event {
    private long id;
    private String name;
    private long ticketPrice;
    private Rating rating;
    private DateTime duration;

    public Event(long id, String name, long ticketPrice, Rating rating, DateTime duration) {
        this.id = id;
        this.name = name;
        this.ticketPrice = ticketPrice;
        this.rating = rating;
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getTicketPrice() {
        return ticketPrice;
    }

    public Rating getRating() {
        return rating;
    }

    public DateTime getDuration() {
        return duration;
    }

    public void setTicketPrice(long ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        return id == event.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", rating=" + rating +
                ", duration=" + duration +
                '}';
    }
}
