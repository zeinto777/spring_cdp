package aspects;

import dao.impl.CounterAspectDAO;
import domain.Event;
import domain.Ticket;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static constants.Constants.*;

/**
 * Created by Andrii_Pinchuk on 2/15/2016.
 */
@Aspect
@Component
public class CounterAspect {
    @Autowired
    CounterAspectDAO counterAspectDAO;

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
        counterAspectDAO.update(eventName, eventId);
    }



}
