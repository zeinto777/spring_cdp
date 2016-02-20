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
        result.forEach(event -> scalingAmountOfEvent(GET_EVENT_BY_NAME, event.getId()));
    }

    @After("execution(* service.IBookingService.bookTicket(..)) && args(ticket) ")
    public void adviceGetBookTicket(Ticket ticket) {
        scalingAmountOfEvent(BOOKED_TICKET, ticket.getEventId());
    }

    @After("execution(* service.IBookingService.getTicketPrice(..)) ")
    public void adviceGetTicketPrice(JoinPoint joinPoint) {
        scalingAmountOfEvent(GET_TICKET_PRICE, Long.valueOf((Long) joinPoint.getArgs()[1]));
    }


    private void scalingAmountOfEvent(String eventName, Long eventId) {
        Map<String, Integer> associatedEvents = counters.getOrDefault(eventId, new HashMap<>());
        Integer count = associatedEvents.getOrDefault(eventName, 0);
        associatedEvents.put(eventName, count + 1);
        counters.putIfAbsent(eventId, associatedEvents);
    }
}
