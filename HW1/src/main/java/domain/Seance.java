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
}
