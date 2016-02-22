package service;

import domain.Auditorium;
import domain.Event;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
public interface IEventService {
    boolean create(Event event);

    boolean remove(long eventId);

    List<Event> getByName(String name);

    Set<Event> getAll();

    Event getById(long eventId);

    boolean assignAuditorium(long eventId, long auditorium_id, DateTime dateTime);
}
