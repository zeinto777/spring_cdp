package aspects;

import domain.Event;
import domain.Ticket;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static constants.Constants.*;

/**
 * Created by Andrii_Pinchuk on 2/15/2016.
 */
@Aspect
@Component
public class CounterAspect {
    private Map<Long, Map<String, Integer>> counters = new HashMap<>();

    @AfterReturning(
            pointcut = "execution(* service.IEventService.getByName(..))",
            returning = "result")
    public void adviceGetEventByName(List<Event> result) {
        if (result.isEmpty()) return;
        Map<String, Integer> associatedEvents;
        for (Event event : result) {
            associatedEvents = !counters.containsKey(event.getId()) ? new HashMap<>() : counters.get(event.getId());
            Integer count = associatedEvents.containsKey(GET_EVENT_BY_NAME) ? associatedEvents.get(GET_EVENT_BY_NAME) + 1 : 0;
            associatedEvents.put(GET_EVENT_BY_NAME, count);
            counters.put(event.getId(), associatedEvents);
        }
    }

    @After("execution(* service.IBookingService.bookTicket(..)) && args(ticket) ")
    public void adviceGetBookTicket(Ticket ticket) {
        Map<String, Integer> associatedEvents;
        associatedEvents = !counters.containsKey(ticket.getEventId()) ? new HashMap<>() : counters.get(ticket.getEventId());
        Integer count = associatedEvents.containsKey(BOOKED_TICKET) ? associatedEvents.get(BOOKED_TICKET) + 1 : 0;
        associatedEvents.put(BOOKED_TICKET, count);
        counters.put(ticket.getEventId(), associatedEvents);
    }


    @After("execution(* service.IBookingService.getTicketPrice(..)) ")
    public void adviceGetTicketPrice(JoinPoint joinPoint) {
        long eventId = Long.valueOf((Long) joinPoint.getArgs()[1]);
        Map<String, Integer> associatedEvents;
        associatedEvents = !counters.containsKey(eventId) ? new HashMap<>() : counters.get(eventId);
        Integer count = associatedEvents.containsKey(GET_TICKET_PRICE) ? associatedEvents.get(GET_TICKET_PRICE) + 1 : 0;
        associatedEvents.put(GET_TICKET_PRICE, count);
        counters.put(eventId, associatedEvents);
    }
}
