package dao.impl;

import dao.IEventDAO;
import domain.Event;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
@Repository
public class EventDAO implements IEventDAO {
    private List<Event> events = new ArrayList<>();

    @Override
    public boolean create(Event event) {
        return events.add(event);
    }

    @Override
    public boolean remove(long eventId) {
        return events.removeIf(event -> event.getId() == eventId);
    }

    @Override
    public List<Event> getByName(String name) {
        List<Event> result = events.stream().filter((event) -> event.getName().equals(name)).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<Event> getAll() {
        return events;
    }


    @Override
    public Event getById(long eventId) {
        Event result = null;
        Iterator<Event> it = events.stream().filter((event) -> event.getId() == eventId).iterator();
        if (it.hasNext()) result = it.next();
        return result;
    }
}
