package service.impl;

import dao.IEventDAO;
import domain.Auditorium;
import domain.Event;
import domain.Seance;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.IAuditoriumService;
import service.IEventService;
import service.ISeanceService;

import java.util.List;

/**
 * Created by Andrii_Pinchuk on 2/6/2016.
 */
@Service
public class EventService implements IEventService {
    @Autowired
    private ISeanceService seanceService;
    @Autowired
    private IAuditoriumService auditoriumService;
    @Autowired
    private IEventDAO eventDAO;

    @Override
    public boolean create(Event event) {
        return eventDAO.create(event);
    }

    @Override
    public boolean remove(long eventId) {
        return eventDAO.remove(eventId);
    }

    @Override
    public List<Event> getByName(String name) {
        return eventDAO.getByName(name);
    }

    @Override
    public List<Event> getAll() {
        return eventDAO.getAll();
    }

    @Override
    public Event getById(long eventId) {
        return eventDAO.getById(eventId);
    }

    @Override
    public boolean assignAuditorium(long eventId, long auditoriumId, DateTime dateTime) {
        Event event = getById(eventId);
        Auditorium auditorium = auditoriumService.getById(auditoriumId);
        if (isAuditoriumEmpty(event, auditorium, dateTime)) {
            return seanceService.create(new Seance(1l, eventId, auditoriumId, dateTime));
        }
        return false;
    }

    private boolean isAuditoriumEmpty(Event event, Auditorium auditorium, DateTime dateTime) {
        boolean result = false;
        List<Seance> seances = seanceService.getAll();
        for (Seance seance : seances) {
            Event assignEvent = getById(seance.getEventId());
            DateTime endOfEvent = seance.getDateTime().plus(countDuration(assignEvent));
            if((seance.getAuditoriumId() == auditorium.getId()) && endOfEvent.isAfter(dateTime) ){
                return result;
            }
        }
        return true;
    }

    private int countDuration(Event assignEvent) {
        return assignEvent.getDuration().getHourOfDay()+ assignEvent.getDuration().getHourOfDay();
    }

}
