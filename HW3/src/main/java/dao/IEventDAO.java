package dao;

import domain.Auditorium;
import domain.Event;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
public interface IEventDAO {
    boolean create(Event event);

    boolean remove(long eventId);

    List<Event> getByName(String name);

    Event getById(long eventId);

    Set<Event> getAll();
}
