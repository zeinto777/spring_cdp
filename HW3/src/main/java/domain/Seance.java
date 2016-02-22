package domain;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
public class Seance {
    private long id;
    private long eventId;
    private long auditoriumId;
    private DateTime dateTime;

    public Seance() {
    }

    public Seance(long id, long eventId, long auditoriumId, DateTime dateTime) {
        this.id = id;
        this.eventId = eventId;
        this.auditoriumId = auditoriumId;
        this.dateTime = dateTime;
    }

    public long getId() {
        return id;
    }

    public long getEventId() {
        return eventId;
    }

    public long getAuditoriumId() {
        return auditoriumId;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public void setAuditoriumId(long auditoriumId) {
        this.auditoriumId = auditoriumId;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Seance{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", auditoriumId=" + auditoriumId +
                ", dateTime=" + dateTime +
                '}';
    }
}
